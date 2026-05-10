package ru.shpg.observability.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shpg.observability.metrics.MetricsService;

@Configuration
public class MetricsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MetricsService metricsService(MeterRegistry registry) {
        return new MetricsService(registry);
    }

}
