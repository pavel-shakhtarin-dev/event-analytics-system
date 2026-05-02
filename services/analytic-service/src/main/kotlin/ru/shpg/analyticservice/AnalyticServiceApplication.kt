package ru.shpg.analyticservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.shpg.analyticservice.config.property.AlertProperties
import ru.shpg.analyticservice.config.property.KafkaProperties
import ru.shpg.analyticservice.config.property.StrategyProperties

@SpringBootApplication
@EnableConfigurationProperties(KafkaProperties::class, AlertProperties::class, StrategyProperties::class)
class AnalyticServiceApplication

fun main(args: Array<String>) {
	runApplication<AnalyticServiceApplication>(*args)
}
