package ru.shpg.analyticservice.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.shpg.analyticservice.model.TransactionEvent

@Component
class EventParser(
    private val objectMapper: ObjectMapper
) {

    fun parse(msg: String): TransactionEvent {
        return objectMapper.readValue(msg, TransactionEvent::class.java)
    }

}