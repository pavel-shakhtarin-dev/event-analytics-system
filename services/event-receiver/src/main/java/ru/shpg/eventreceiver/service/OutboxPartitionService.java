package ru.shpg.eventreceiver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboxPartitionService {

    private final JdbcTemplate jdbcTemplate;

    public void createPartitionsIfNotExists() {
        jdbcTemplate.execute("""
                    SELECT event.create_outbox_partition(CURRENT_DATE)
                """);

        jdbcTemplate.execute("""
                    SELECT event.create_outbox_partition(CURRENT_DATE + 1)
                """);
    }

}