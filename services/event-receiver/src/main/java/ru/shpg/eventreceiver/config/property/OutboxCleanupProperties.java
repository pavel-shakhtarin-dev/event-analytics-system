package ru.shpg.eventreceiver.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "app.outbox.cleanup")
@Getter
@Setter
public class OutboxCleanupProperties {

    private boolean enabled = true;
    private Duration retention = Duration.ofHours(24);
    private int maxIterations = 100;
    private int batchSize = 1000;

}
