package ru.shpg.observability.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.observability")
@Data
public class ObservabilityProperties {

    private boolean enabled = true;

    private String serviceName = "unknown-service";

    private boolean metricsEnabled = true;

    private boolean tracingEnabled = true;

    private boolean loggingEnabled = true;

}