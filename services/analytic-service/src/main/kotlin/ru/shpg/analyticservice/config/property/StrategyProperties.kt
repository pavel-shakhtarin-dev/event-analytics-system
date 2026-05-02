package ru.shpg.analyticservice.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "app.analytics")
data class StrategyProperties(
    val window: Duration
)