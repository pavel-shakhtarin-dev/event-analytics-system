package ru.shpg.observability.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MetricsService {

    private final MeterRegistry meterRegistry;

    public void incrementCounter(String name) {
        meterRegistry.counter(name).increment();
    }

    public void incrementCounter(String name, Tags tags) {
        meterRegistry.counter(name, tags).increment();
    }

}
