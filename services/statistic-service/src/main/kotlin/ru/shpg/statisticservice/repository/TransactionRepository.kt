package ru.shpg.statisticservice.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.shpg.statisticservice.model.TransactionEvent
import ru.shpg.statisticservice.model.UserStats
import java.sql.Timestamp
import java.time.Instant

@Repository
class TransactionRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun save(event: TransactionEvent) {
        jdbcTemplate.update(
            """
            INSERT INTO transactions (userId, amount, timestamp)
            VALUES (?, ?, ?)
            """.trimIndent(),
            event.userId,
            event.amount,
            Timestamp.from(event.timestamp)
        )
    }

    fun getStats(from: Instant, to: Instant): List<UserStats> {
        return jdbcTemplate.query(
            """
            SELECT userId, sum(amount) as total
            FROM transactions
            WHERE timestamp BETWEEN ? AND ?
            GROUP BY userId
            """.trimIndent(),
            { rs, _ ->
                UserStats(
                    userId = rs.getString("userId"),
                    total = rs.getBigDecimal("total")
                )
            },
            Timestamp.from(from),
            Timestamp.from(to)
        )
    }
}