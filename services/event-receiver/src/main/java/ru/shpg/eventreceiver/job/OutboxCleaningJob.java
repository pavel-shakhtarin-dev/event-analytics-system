package ru.shpg.eventreceiver.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.shpg.eventreceiver.config.property.OutboxCleanupProperties;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxCleaningJob {

    private final OutboxCleanupWorker worker;
    private final OutboxCleanupProperties props;

    @Scheduled(cron = "${app.outbox.cleanup.cron}")
    @SchedulerLock(
            name = "outboxPartitionJob",
            lockAtMostFor = "${app.outbox.cleanup.shed-lock-at-most}",
            lockAtLeastFor = "${app.outbox.cleanup.shed-lock-at-least}"
    )
    public void cleanup() {
        if (!props.isEnabled()) {
            return;
        }
        var threshold = Instant.now().minus(props.getRetention());
        int totalDeleted = 0;
        int iterations = 0;

        while (true) {
            int deleted = worker.deleteBatch(threshold, props.getBatchSize());
            if (deleted == 0) {
                break;
            }
            totalDeleted += deleted;
            iterations++;
            if (iterations >= props.getMaxIterations()) {
                log.warn("Outbox cleanup stopped by maxIterations limit");
                break;
            }
        }

        if (totalDeleted > 0) {
            log.info("Outbox cleanup: deleted {} records in {} iterations (threshold={})",
                    totalDeleted, iterations, threshold);
        }
    }

}