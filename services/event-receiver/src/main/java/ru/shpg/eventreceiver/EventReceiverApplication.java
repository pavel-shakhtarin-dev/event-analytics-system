package ru.shpg.eventreceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.shpg.eventreceiver.config.property.OutboxCleanupProperties;

@SpringBootApplication
@EnableConfigurationProperties(OutboxCleanupProperties.class)
public class EventReceiverApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventReceiverApplication.class, args);
    }

}
