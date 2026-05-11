package ru.shpg.simulation.model;

import java.util.Map;
import java.util.UUID;

public record EventRequest(
        UUID eventId,
        UUID userId,
        String type,
        Long timestamp,
        Map<String, Object> payload
) {
}
