package ru.shpg.statisticservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.shpg.statisticservice.model.UserStats
import ru.shpg.statisticservice.service.StatisticService

@RestController
@RequestMapping("/stats")
class StatisticController(
    private val statisticService: StatisticService
) {

    @GetMapping
    fun getStats(@RequestParam from: Long, @RequestParam to: Long): List<UserStats> {
        return statisticService.getStats(from, to)
    }

}