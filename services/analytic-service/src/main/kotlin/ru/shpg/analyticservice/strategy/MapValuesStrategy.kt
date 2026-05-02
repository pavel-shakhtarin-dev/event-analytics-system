package ru.shpg.analyticservice.strategy

import org.apache.kafka.streams.kstream.KStream
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import ru.shpg.analyticservice.model.TransactionEvent
import ru.shpg.analyticservice.model.TransactionMetricResult

@Component
@ConditionalOnProperty(name = ["app.analytics.strategy"], havingValue = "mapValues")
class MapValuesStrategy : AnalyticsStrategy {

    override fun process(stream: KStream<String, TransactionEvent>): KStream<String, TransactionMetricResult> {
        return stream.mapValues { key, value -> TransactionMetricResult(key, value.amount) }
    }

}