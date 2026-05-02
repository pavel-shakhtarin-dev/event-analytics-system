package ru.shpg.observability.logging;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingObservationHandler implements ObservationHandler<Observation.Context> {

    @Override
    public void onStart(Observation.Context context) {
        log.info("START {}", context.getName());
    }

    @Override
    public void onError(Observation.Context context) {
        log.error("ERROR {}", context.getName(), context.getError());
    }

    @Override
    public void onStop(Observation.Context context) {
        if (context.getError() == null) {
            log.info("SUCCESS {}", context.getName());
        }
    }

    @Override
    public boolean supportsContext(@Nonnull Observation.Context context) {
        return true;
    }

}