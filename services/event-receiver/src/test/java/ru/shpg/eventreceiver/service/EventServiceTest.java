package ru.shpg.eventreceiver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shpg.eventreceiver.entity.OutboxEvent;
import ru.shpg.eventreceiver.mapper.OutboxEventMapper;
import ru.shpg.eventreceiver.model.EventRequest;
import ru.shpg.eventreceiver.repository.OutboxRepository;
import ru.shpg.eventreceiver.security.util.UserProvider;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private OutboxRepository repository;

    @Mock
    private OutboxEventMapper outboxEventMapper;

    @Mock
    private UserProvider userProvider;

    @InjectMocks
    private EventService eventService;

    @Test
    @DisplayName("Should successfully process event")
    void shouldProcessEventSuccessfully() {
        // given
        var eventId = UUID.randomUUID();
        var userId = "test-user-123";
        var request = new EventRequest(eventId, "TEST", 1713711600000L, null);
        var entity = new OutboxEvent(); // Предположим, это твоя сущность

        when(userProvider.getUserId()).thenReturn(userId);
        when(outboxEventMapper.toEntity(any(EventRequest.class), eq(userId))).thenReturn(entity);

        // when
        eventService.process(request);

        // then
        verify(userProvider, times(1)).getUserId();
        verify(outboxEventMapper, times(1)).toEntity(request, userId);
        verify(repository, times(1)).save(entity);

        verifyNoMoreInteractions(userProvider, outboxEventMapper, repository);
    }

    @Test
    @DisplayName("Should throw exception when UserProvider fails")
    void shouldThrowExceptionWhenUserProviderFails() {
        // given
        var request = new EventRequest(UUID.randomUUID(), "TEST", 1L, null);
        when(userProvider.getUserId()).thenThrow(new RuntimeException("Security error"));

        // when & then
        Assertions.assertThrows(RuntimeException.class, () -> eventService.process(request));

        verify(repository, never()).save(any());
    }


}
