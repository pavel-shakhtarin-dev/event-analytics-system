package ru.shpg.statisticservice.model

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class TransactionAmount(
    val amount: BigDecimal
)

data class TransactionEvent(
    val userId: UUID,
    val timestamp: Instant,
    val amount: BigDecimal
)
