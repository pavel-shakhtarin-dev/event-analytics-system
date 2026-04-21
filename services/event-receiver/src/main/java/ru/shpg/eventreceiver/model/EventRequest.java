package ru.shpg.eventreceiver.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;


public record EventRequest(

        @NotNull
        UUID eventId,

        @NotBlank
        String type,

        @NotNull
        Long timestamp,

        Map<String, Object> payload

) {
}
