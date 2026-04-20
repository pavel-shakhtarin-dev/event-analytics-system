package ru.shpg.eventreceiver.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.shpg.eventreceiver.service.OutboxPartitionService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPartitionScheduler {

    private final OutboxPartitionService partitionService;

    private final JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "${outbox.partition.cron}")
    @SchedulerLock(
            name = "outboxPartitionJob",
            lockAtMostFor = "10m",
            lockAtLeastFor = "1m"
    )
    public void managePartitions() {

        log.info("Starting partition management");

        partitionService.createPartitionsIfNotExists();

        jdbcTemplate.execute("""
                    SELECT drop_old_outbox_partitions(INTERVAL '24 hours')
                """);

        log.info("Partition management completed");
    }

}