package ru.shpg.statisticservice.model

import java.math.BigDecimal

data class UserStats(
    val userId: String,
    val total: BigDecimal
)