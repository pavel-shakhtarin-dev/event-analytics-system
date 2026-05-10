package ru.shpg.analyticservice.config.property

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "app.kafka")
data class KafkaProperties(
    val transactionsTopic: String,
    val alertsTopic: String
)