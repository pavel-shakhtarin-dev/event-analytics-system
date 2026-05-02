package ru.shpg.statisticservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StatisticServiceApplication

fun main(args: Array<String>) {
	runApplication<StatisticServiceApplication>(*args)
}
