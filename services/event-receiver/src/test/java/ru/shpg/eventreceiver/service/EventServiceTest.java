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
import ru.shpg.observability.metrics.MetricsService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.shpg.eventreceiver.utils.ObjectUtil.createEventRequest;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private OutboxRepository repository;

    @Mock
    private OutboxEventMapper outboxEventMapper;

    @Mock
    private MetricsService metricsService;

    @InjectMocks
    private EventService eventService;

    @Test
    @DisplayName("Should successfully process event")
    void shouldProcessEventSuccessfully() {
        // given
        var request = createEventRequest();
        var entity = new OutboxEvent();

        when(outboxEventMapper.toEntity(any(EventRequest.class))).thenReturn(new OutboxEvent());

        // when
        eventService.process(request);

        // then
        verify(outboxEventMapper, times(1)).toEntity(request);
        verify(repository, times(1)).save(entity);

        verifyNoMoreInteractions(outboxEventMapper, repository);
    }

    @Test
    @DisplayName("Should throw exception when DB fails")
    void shouldThrowExceptionWhenDBFails() {
        // given
        var request = createEventRequest();
        when(repository.save(any())).thenThrow(new RuntimeException("DB error"));

        // when & then
        Assertions.assertThrows(RuntimeException.class, () -> eventService.process(request));
    }


}
