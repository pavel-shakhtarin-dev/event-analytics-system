package ru.shpg.statisticservice.model

import java.math.BigDecimal
import java.time.Instant

data class TransactionAmount(
    val amount: BigDecimal
)

data class TransactionEvent(
    val userId: String,
    val timestamp: Instant,
    val amount: BigDecimal
)
