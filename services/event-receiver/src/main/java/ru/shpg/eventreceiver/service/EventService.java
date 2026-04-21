package ru.shpg.eventreceiver.service;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shpg.eventreceiver.mapper.OutboxEventMapper;
import ru.shpg.eventreceiver.model.EventRequest;
import ru.shpg.eventreceiver.repository.OutboxRepository;
import ru.shpg.eventreceiver.security.util.UserProvider;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final OutboxRepository repository;

    private final OutboxEventMapper outboxEventMapper;

    private final UserProvider userProvider;

    @Observed(
            name = "event.process",
            contextualName = "process-event",
            lowCardinalityKeyValues = {
                    "layer", "service",
                    "operation", "process"
            }
    )
    @Transactional
    public void process(EventRequest request) {
        var userId = userProvider.getUserId();

        log.info("event_received eventId={} userId={}", request.eventId(), userId);

        var event = outboxEventMapper.toEntity(request, userId);

        repository.save(event);
    }

}
