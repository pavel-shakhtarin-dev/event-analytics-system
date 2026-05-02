package ru.shpg.analyticservice.strategy

import org.apache.kafka.streams.kstream.KStream
import ru.shpg.analyticservice.model.TransactionEvent
import ru.shpg.analyticservice.model.TransactionMetricResult

interface AnalyticsStrategy {


    fun process(stream: KStream<String, TransactionEvent>): KStream<String, TransactionMetricResult>

}