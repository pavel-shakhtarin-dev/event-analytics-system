package ru.shpg.statisticservice.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.shpg.statisticservice.model.TransactionEvent
import ru.shpg.statisticservice.model.UserStats
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Repository
class TransactionRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun save(event: TransactionEvent) {
        jdbcTemplate.update(
            """
            INSERT INTO transactions (userId, amount, transactionTimestamp)
            VALUES (?, ?, ?)
            """.trimIndent(),
            event.userId,
            event.amount,
            Timestamp.from(event.timestamp)
        )
    }

    fun getStats(from: Instant, to: Instant): List<UserStats> {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneOffset.UTC)

        return jdbcTemplate.query(
            """
            SELECT userId, sum(amount) as total
            FROM transactions
            WHERE transactionTimestamp BETWEEN toDateTime64(?, 3) AND toDateTime64(?, 3)
            GROUP BY userId
            """.trimIndent(),
            { rs, _ ->
                UserStats(
                    userId = rs.getString("userId"),
                    total = rs.getBigDecimal("total")
                )
            },
            formatter.format(from),
            formatter.format(to)
        )
    }
}