package ru.shpg.analyticservice.model

import java.math.BigDecimal

data class Alert(
    val amount: BigDecimal,
    val threshold: BigDecimal,
    val timestamp: Long
)
