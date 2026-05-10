package ru.shpg.statisticservice.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import ru.shpg.statisticservice.model.TransactionEvent
import ru.shpg.statisticservice.service.StatisticService
import ru.shpg.statisticservice.util.TransactionParser
import java.time.Instant
import java.util.UUID

@Component
@Suppress("unused")
class TransactionListener(
    private val statisticService: StatisticService,
    private val transactionParser: TransactionParser
) {

    @KafkaListener(topics = ["\${app.kafka.topic}"])
    fun listen(
        msg: String,
        @Header(KafkaHeaders.RECEIVED_KEY) userId: String,
        @Header("event_timestamp") timestamp: String
    ) {
        val transactionAmount = transactionParser.parseTransaction(msg)
        val event = TransactionEvent(
            userId = UUID.fromString(userId),
            timestamp = Instant.ofEpochMilli(timestamp.toLong()),
            amount = transactionAmount.amount
        )
        statisticService.saveStats(event)
    }

}