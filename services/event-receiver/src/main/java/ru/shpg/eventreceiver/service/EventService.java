package ru.shpg.eventreceiver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shpg.eventreceiver.mapper.OutboxEventMapper;
import ru.shpg.eventreceiver.model.EventRequest;
import ru.shpg.eventreceiver.repository.OutboxRepository;
import ru.shpg.observability.metrics.MetricsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final OutboxRepository repository;

    private final OutboxEventMapper outboxEventMapper;

    private final MetricsService metricsService;

    @Transactional
    public void process(EventRequest request) {

        log.info("event_received eventId={} userId={}", request.eventId(), request.userId());

        var event = outboxEventMapper.toEntity(request);

        metricsService.incrementCounter("app_event", "type", request.type(), "userId", String.valueOf(request.userId()));

        repository.save(event);
    }

}
