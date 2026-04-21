package ru.shpg.eventreceiver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shpg.eventreceiver.model.EventRequest;
import ru.shpg.eventreceiver.service.EventService;

@Tag(name = "Event Ingestion", description = "Методы для приема событий")
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Принять новое событие", description = "Сохраняет событие в Outbox таблицу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Событие принято"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
            @ApiResponse(responseCode = "409", description = "Событие с таким ID уже существует")
    })
    @PostMapping
    public ResponseEntity<Void> ingestEvent(@Valid @RequestBody EventRequest request) {

        log.info("http_request eventId={}", request.eventId());

        eventService.process(request);

        return ResponseEntity.accepted().build();
    }

}
