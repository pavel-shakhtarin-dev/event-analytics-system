package ru.shpg.analyticservice.config

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.streams.processor.TimestampExtractor
import org.springframework.stereotype.Component
import ru.shpg.analyticservice.util.logger

@Component
class EventTimestampExtractor : TimestampExtractor {

    private val log = logger()

    override fun extract(record: ConsumerRecord<Any, Any>, partitionTime: Long): Long {
        val header = record.headers().lastHeader("timestamp") ?: return partitionTime
        return try {
            String(header.value()).toLong()
        } catch (e: Exception) {
            log.error("Extraction error: ${e.message}")
            partitionTime
        }
    }

}