
package com.flashsale.event.serviceTest;

import com.flashsale.event.entity.Event;
import com.flashsale.event.entity.dto.EventRequestDto;
import com.flashsale.event.entity.dto.EventResponseDto;
import com.flashsale.event.entity.EventStatus;
import com.flashsale.event.exception.ResourceNotFoundException;
import com.flashsale.event.mapper.EventMapper;
import com.flashsale.event.repository.EventRepository;
import com.flashsale.event.service.Impementation.EventServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private UUID eventId;
    private Event event;
    private EventRequestDto requestDto;
    private EventResponseDto responseDto;

    @BeforeEach
    void setUp() {

        eventId = UUID.randomUUID();

        event = new Event();
        requestDto = new EventRequestDto();

        responseDto = EventResponseDto.builder()
                .id(eventId)
                .name("Concert")
                .venue("Bangalore")
                .startsAt(Instant.now())
                .status(EventStatus.DRAFT)
                .createdAt(Instant.now())
                .build();
    }

    // 1. Test Create Event - Success
    @Test
    void createEvent_ShouldReturnSavedEvent() {

        when(eventMapper.toEvent(requestDto))
                .thenReturn(event);

        when(eventRepository.save(event))
                .thenReturn(event);

        when(eventMapper.toResponseDto(event))
                .thenReturn(responseDto);

        EventResponseDto result = eventService.createEvent(requestDto);

        assertNotNull(result);
        assertEquals(responseDto, result);
        assertEquals("Concert", result.getName());
        assertEquals("Bangalore", result.getVenue());

        verify(eventMapper).toEvent(requestDto);
        verify(eventRepository).save(event);
        verify(eventMapper).toResponseDto(event);
    }

    // 2. Test Get Event By ID - Success
    @Test
    void getEvent_ShouldReturnEvent_WhenIdExists() {

        when(eventRepository.findById(eventId))
                .thenReturn(Optional.of(event));

        when(eventMapper.toResponseDto(event))
                .thenReturn(responseDto);

        EventResponseDto result = eventService.getEvent(eventId);

        assertNotNull(result);
        assertEquals(responseDto, result);
        assertEquals("Concert", result.getName());

        verify(eventRepository).findById(eventId);
        verify(eventMapper).toResponseDto(event);
    }

    // 3. Test Get Event By ID - Not Found
    @Test
    void getEvent_ShouldThrowException_WhenIdDoesNotExist() {

        when(eventRepository.findById(eventId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.getEvent(eventId)
        );

        verify(eventRepository).findById(eventId);

        verify(eventMapper, never())
                .toResponseDto(any(Event.class));
    }

    // 4. Test Get All Events - Success
    @Test
    void getAllEvent_ShouldReturnAllEvents() {

        Event event1 = new Event();
        Event event2 = new Event();

        EventResponseDto response1 = EventResponseDto.builder()
                .id(UUID.randomUUID())
                .name("Concert")
                .venue("Bangalore")
                .startsAt(Instant.now())
                .status(EventStatus.DRAFT)
                .createdAt(Instant.now())
                .build();

        EventResponseDto response2 = EventResponseDto.builder()
                .id(UUID.randomUUID())
                .name("Music Festival")
                .venue("Mumbai")
                .startsAt(Instant.now())
                .status(EventStatus.DRAFT)
                .createdAt(Instant.now())
                .build();

        when(eventRepository.findAll())
                .thenReturn(List.of(event1, event2));

        when(eventMapper.toResponseDto(event1))
                .thenReturn(response1);

        when(eventMapper.toResponseDto(event2))
                .thenReturn(response2);

        List<EventResponseDto> result = eventService.getAllEvent();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Concert", result.get(0).getName());
        assertEquals("Music Festival", result.get(1).getName());

        verify(eventRepository).findAll();
        verify(eventMapper, times(2))
                .toResponseDto(any(Event.class));
    }

    // 5. Test Get All Events - Empty List
    @Test
    void getAllEvent_ShouldReturnEmptyList_WhenNoEventsExist() {

        when(eventRepository.findAll())
                .thenReturn(List.of());

        List<EventResponseDto> result = eventService.getAllEvent();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(eventRepository).findAll();
        verify(eventMapper, never())
                .toResponseDto(any(Event.class));
    }

    // 6. Test Update Event - Success
    @Test
    void updateEvent_ShouldUpdateExistingEvent() {

        when(eventRepository.findById(eventId))
                .thenReturn(Optional.of(event));

        eventService.updateEvent(requestDto, eventId);

        verify(eventRepository).findById(eventId);

        verify(eventMapper).updateEvent(requestDto, event);
    }

    // 7. Test Update Event - Not Found
    @Test
    void updateEvent_ShouldThrowException_WhenIdDoesNotExist() {

        when(eventRepository.findById(eventId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.updateEvent(requestDto, eventId)
        );

        verify(eventRepository).findById(eventId);

        verify(eventMapper, never())
                .updateEvent(any(EventRequestDto.class), any(Event.class));
    }
}
