package ru.shpg.analyticservice.model

import java.math.BigDecimal

data class TransactionMetricResult(
    val userId: String,
    val sum: BigDecimal
)
