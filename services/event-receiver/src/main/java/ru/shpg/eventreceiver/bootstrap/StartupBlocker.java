package ru.shpg.eventreceiver.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.shpg.eventreceiver.service.OutboxPartitionService;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartupBlocker implements ApplicationRunner {

    private final OutboxPartitionService partitionService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Application started: partition init started");
        partitionService.createPartitionsIfNotExists();
        log.info("Application started: partition init ended");
    }

}
