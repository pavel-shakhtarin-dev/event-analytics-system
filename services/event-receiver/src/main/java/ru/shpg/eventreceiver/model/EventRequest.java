package ru.shpg.eventreceiver.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record EventRequest(

        @NotBlank
        String eventId,

        @NotBlank
        String type,

        @NotNull
        Long timestamp,

        Map<String, Object> metadata

) {
}
