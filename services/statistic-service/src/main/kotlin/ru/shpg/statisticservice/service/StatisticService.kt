package ru.shpg.statisticservice.service

import org.springframework.stereotype.Service
import ru.shpg.statisticservice.model.TransactionEvent
import ru.shpg.statisticservice.model.UserStats
import ru.shpg.statisticservice.repository.TransactionRepository
import java.time.Instant

@Service
class StatisticService(
    private val transactionRepository: TransactionRepository
) {

    fun getStats(from: Long, to: Long): List<UserStats> {
        return transactionRepository.getStats(Instant.ofEpochMilli(from), Instant.ofEpochMilli(to))
    }

    fun saveStats(event: TransactionEvent) {
        transactionRepository.save(event)
    }

}