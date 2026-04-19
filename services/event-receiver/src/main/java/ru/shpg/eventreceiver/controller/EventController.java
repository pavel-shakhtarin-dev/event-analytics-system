package ru.shpg.eventreceiver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shpg.eventreceiver.model.EventRequest;
import ru.shpg.eventreceiver.service.EventService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Void> ingestEvent(@Valid @RequestBody EventRequest request) {

        log.info("http_request eventId={}", request.eventId());

        eventService.process(request);

        return ResponseEntity.accepted().build();
    }

}
