package ru.shpg.eventreceiver.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shpg.eventreceiver.model.EventRequest;
import ru.shpg.eventreceiver.repository.OutboxRepository;
import ru.shpg.eventreceiver.service.EventService;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EventServiceIT extends BaseIntegrationTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private OutboxRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should save event to database")
    @SuppressWarnings("unchecked")
    void shouldSaveEventToDb() throws Exception {
        // given
        UUID eventId = UUID.randomUUID();
        EventRequest request = new EventRequest(
                eventId,
                UUID.randomUUID(),
                "TEST",
                System.currentTimeMillis(),
                Map.of("key", "val")
        );

        // when
        eventService.process(request);

        // then
        var savedEvent = repository.findById(eventId).orElseThrow();
        Map<String, Object> payloadMap = objectMapper.readValue(savedEvent.getPayload(), Map.class);

        assertThat(savedEvent.getId()).isEqualTo(eventId);
        assertThat(savedEvent.getEventType()).isEqualTo("TEST");
        assertThat(payloadMap).containsEntry("key", "val");
    }

}

