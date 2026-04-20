package ru.shpg.eventreceiver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.shpg.eventreceiver.model.EventRequest;
import ru.shpg.eventreceiver.service.EventService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.shpg.eventreceiver.testutils.ObjectUtil.createEventRequest;
import static ru.shpg.eventreceiver.testutils.ObjectUtil.createInvalidEventRequest;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    private MockMvc mockMvc;

    private EventService eventService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        eventService = mock(EventService.class);
        objectMapper = new ObjectMapper().findAndRegisterModules();
        EventController eventController = new EventController(eventService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(eventController)
                .build();
    }

    @Test
    void shouldAcceptEvent() throws Exception {
        EventRequest request = createEventRequest();

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());

        verify(eventService, times(1)).process(any(EventRequest.class));
    }

    @Test
    void shouldNotCallService_whenRequestInvalid() throws Exception {
        String invalidJson = "{}";

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(eventService);
    }

    @Test
    void shouldNotCallService_whenValidationFailed() throws Exception {
        EventRequest eventRequest = createInvalidEventRequest();

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(eventService);
    }

}