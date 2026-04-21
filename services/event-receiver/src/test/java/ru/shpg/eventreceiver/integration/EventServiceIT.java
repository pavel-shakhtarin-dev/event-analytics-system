package ru.shpg.eventreceiver.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.shpg.eventreceiver.model.EventRequest;
import ru.shpg.eventreceiver.repository.OutboxRepository;
import ru.shpg.eventreceiver.security.util.UserProvider;
import ru.shpg.eventreceiver.service.EventService;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class EventServiceIT extends BaseIntegrationTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private OutboxRepository repository;

    @MockitoBean
    private UserProvider userProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should save event to database")
    @SuppressWarnings("unchecked")
    void shouldSaveEventToDb() throws Exception {
        // given
        UUID eventId = UUID.randomUUID();
        String mockUserId = "test-user-id";
        EventRequest request = new EventRequest(eventId, "TEST", System.currentTimeMillis(), Map.of("key", "val"));

        when(userProvider.getUserId()).thenReturn(mockUserId);

        // when
        eventService.process(request);

        // then
        var savedEvent = repository.findById(eventId).orElseThrow();
        Map<String, Object> payloadMap = objectMapper.readValue(savedEvent.getPayload(), Map.class);

        assertThat(savedEvent.getId()).isEqualTo(eventId);
        assertThat(savedEvent.getAggregateType()).isEqualTo("TEST");
        assertThat(payloadMap).containsEntry("key", "val");
    }

}

