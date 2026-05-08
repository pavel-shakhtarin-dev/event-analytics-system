package ru.shpg.statisticservice.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.shpg.statisticservice.model.TransactionAmount

@Component
class TransactionParser(
    private val objectMapper: ObjectMapper
) {

    fun parseTransaction(raw: String): TransactionAmount {
        return try {
            objectMapper.readValue(raw, TransactionAmount::class.java)
        } catch (_: Exception) {
            val unescaped = objectMapper.readValue(raw, String::class.java)
            objectMapper.readValue(unescaped, TransactionAmount::class.java)
        }
    }

}