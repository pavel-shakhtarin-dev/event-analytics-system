package ru.shpg.observability.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shpg.observability.logging.LoggingObservationHandler;

@Configuration
@ConditionalOnProperty(
        value = "app.observability.logging-enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LoggingObservationHandler loggingObservationHandler() {
        return new LoggingObservationHandler();
    }

}
