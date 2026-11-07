
package com.flashsale.event.ControllerTest;

import tools.jackson.databind.ObjectMapper;

import com.flashsale.event.controller.EventController;
import com.flashsale.event.entity.EventStatus;
import com.flashsale.event.entity.dto.EventRequestDto;
import com.flashsale.event.entity.dto.EventResponseDto;
import com.flashsale.event.service.EventService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    private EventRequestDto request;

    private EventResponseDto response;

    // Runs before every test
    @BeforeEach
    void setUp() {

        // Valid request DTO
        request = new EventRequestDto();

        request.setName("Concert");
        request.setVenue("Bangalore");
        request.setStartsAt(Instant.now().plusSeconds(86400));
        request.setStatus(EventStatus.DRAFT);

        // Response DTO
        response = EventResponseDto.builder()
                .id(UUID.randomUUID())
                .name("Concert")
                .venue("Bangalore")
                .startsAt(request.getStartsAt())
                .status(EventStatus.DRAFT)
                .createdAt(Instant.now())
                .build();
    }

    // 1. Test Create Event
    @Test
    void createEvent_ShouldReturn201() throws Exception {

        when(eventService.createEvent(any(EventRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(eventService, times(1))
                .createEvent(any(EventRequestDto.class));
    }

    // 2. Test Get Event By UUID
    @Test
    void getEvent_ShouldReturn200() throws Exception {

        UUID uuid = response.getId();

        when(eventService.getEvent(uuid))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/event/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Concert"))
                .andExpect(jsonPath("$.venue").value("Bangalore"));

        verify(eventService, times(1)).getEvent(uuid);
    }

    // 3. Test Get All Events
    @Test
    void getAllEvents_ShouldReturn200() throws Exception {

        List<EventResponseDto> responses = List.of(
                response,
                EventResponseDto.builder()
                        .id(UUID.randomUUID())
                        .name("Music Festival")
                        .venue("Mumbai")
                        .startsAt(Instant.now().plusSeconds(172800))
                        .status(EventStatus.DRAFT)
                        .createdAt(Instant.now())
                        .build()
        );

        when(eventService.getAllEvent())
                .thenReturn(responses);

        mockMvc.perform(get("/api/v1/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(eventService, times(1)).getAllEvent();
    }

    // 4. Test Update Event
    @Test
    void updateEvent_ShouldReturn204() throws Exception {

        UUID uuid = response.getId();

        doNothing().when(eventService)
                .updateEvent(any(EventRequestDto.class), eq(uuid));

        mockMvc.perform(put("/api/v1/event/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(eventService, times(1))
                .updateEvent(any(EventRequestDto.class), eq(uuid));
    }
}
