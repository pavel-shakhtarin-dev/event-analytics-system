package ru.shpg.eventreceiver.job;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OutboxCleanupWorker {

    private final JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int deleteBatch(Instant threshold, int batchSize) {

        return jdbcTemplate.update("""
                    WITH to_delete AS (
                        SELECT id
                        FROM event.outbox_event
                        WHERE created_at < ?
                        ORDER BY created_at
                        LIMIT ?
                    )
                    DELETE FROM event.outbox_event o
                    USING to_delete d
                    WHERE o.id = d.id
                """, threshold, batchSize);
    }

}
