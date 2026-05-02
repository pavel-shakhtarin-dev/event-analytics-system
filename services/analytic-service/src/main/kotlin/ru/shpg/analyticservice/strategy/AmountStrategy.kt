package ru.shpg.analyticservice.strategy

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.TimeWindows
import org.apache.kafka.streams.state.WindowStore
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import ru.shpg.analyticservice.config.property.StrategyProperties
import ru.shpg.analyticservice.model.TransactionEvent
import ru.shpg.analyticservice.model.TransactionMetricResult
import ru.shpg.analyticservice.util.bigDecimalSerde
import java.math.BigDecimal

@Component
@ConditionalOnProperty(name = ["app.analytics.strategy"], havingValue = "amount")
class AmountStrategy(
    val strategyProperties: StrategyProperties
) : AnalyticsStrategy {

    override fun process(stream: KStream<String, TransactionEvent>): KStream<String, TransactionMetricResult> {

        val materializedStore = Materialized.`as`<String, BigDecimal, WindowStore<Bytes, ByteArray>>("transaction-sums-store")
            .withKeySerde(Serdes.String())
            .withValueSerde(bigDecimalSerde())

        return stream
            .groupByKey()
            .windowedBy(TimeWindows.ofSizeWithNoGrace(strategyProperties.window))
            .aggregate(
                { BigDecimal.ZERO },
                { _, event, agg -> agg.plus(event.amount) },
                materializedStore
            )
            .toStream()
            .map { key, sum ->
                KeyValue(
                    key.key(),
                    TransactionMetricResult(
                        userId = key.key(),
                        sum = sum
                    )
                )
            }
    }

}