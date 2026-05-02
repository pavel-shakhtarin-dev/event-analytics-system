package ru.shpg.analyticservice.config

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.support.serializer.JsonSerde
import ru.shpg.analyticservice.config.property.KafkaProperties
import ru.shpg.analyticservice.service.AlertService
import ru.shpg.analyticservice.strategy.AnalyticsStrategy
import ru.shpg.analyticservice.util.EventParser

@Configuration
@EnableKafkaStreams
class AnalyticsTopology(
    private val eventParser: EventParser,
    private val strategy: AnalyticsStrategy,
    private val timestampExtractor: EventTimestampExtractor,
    private val alertService: AlertService,
    private val kafkaProperties: KafkaProperties
) {

    @Bean
    fun topology(builder: StreamsBuilder): Topology {
        val consumed = Consumed
            .with(Serdes.String(), JsonSerde(String::class.java))
            .withTimestampExtractor(timestampExtractor)

        builder
            .stream(kafkaProperties.transactionsTopic, consumed)
            .mapValues { msg -> eventParser.parse(msg) }
            .let { event -> strategy.process(event) }
            .mapValues { value -> alertService.evaluate(value) }
            .filter { _, alert -> alert != null }
            .to(kafkaProperties.alertsTopic)

        return builder.build()
    }

}