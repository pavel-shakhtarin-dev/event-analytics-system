package ru.shpg.analyticservice.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import java.math.BigDecimal

@ConfigurationProperties(prefix = "app.alert")
data class AlertProperties(
    val threshold: BigDecimal
)