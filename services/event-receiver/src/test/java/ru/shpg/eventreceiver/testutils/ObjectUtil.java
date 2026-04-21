package ru.shpg.eventreceiver.testutils;

import ru.shpg.eventreceiver.model.EventRequest;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class ObjectUtil {

    public static EventRequest createEventRequest() {
        return new EventRequest(
                UUID.randomUUID(),
                "TEST",
                Instant.now().toEpochMilli(),
                Map.of()
        );
    }

    public static EventRequest createInvalidEventRequest() {
        return new EventRequest(
                null,
                "TEST",
                Instant.now().toEpochMilli(),
                Map.of()
        );
    }

}
