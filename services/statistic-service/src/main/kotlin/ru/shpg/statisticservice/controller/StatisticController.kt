package ru.shpg.statisticservice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.shpg.statisticservice.model.UserStats
import ru.shpg.statisticservice.service.StatisticService

@Tag(name = "Stats Request", description = "Методы получения статистики")
@RestController
@RequestMapping("/stats")
class StatisticController(
    private val statisticService: StatisticService
) {

    @Operation(
        summary = "Получить статистику",
        description = "Возвращает агрегированную статистику по пользователям за указанный период"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Статистика успешно получена"),
            ApiResponse(responseCode = "400", description = "Некорректные параметры запроса")
        ]
    )
    @GetMapping
    fun getStats(@RequestParam from: Long, @RequestParam to: Long): List<UserStats> {
        return statisticService.getStats(from, to)
    }

}