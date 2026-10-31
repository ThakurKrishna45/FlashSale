package com.flashsale.event.service.Impementation;

import com.flashsale.event.entity.Event;
import com.flashsale.event.entity.dto.EventRequestDto;
import com.flashsale.event.entity.dto.EventResponseDto;
import com.flashsale.event.exception.ResourceNotFoundException;
import com.flashsale.event.mapper.EventMapper;
import com.flashsale.event.repository.EventRepository;
import com.flashsale.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

//    Method to create Event
    @Override
    @Transactional
    public EventResponseDto createEvent(EventRequestDto event) {
        Event entity = eventMapper.toEntity(event);
        Event savedEntity = eventRepository.save(entity);
        return eventMapper.toResponseDto(savedEntity);
    }

//    Method to Fetch Event using its uuid that will come from frontend
    @Override
    @Transactional(readOnly = true)
    public EventResponseDto getEvent(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Id does not exist"));
        return eventMapper.toResponseDto(event);
    }

//    Method to Fetch All Events
    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDto> getAllEvent() {
        List<EventResponseDto> eventList =
                eventRepository.
                        findAll().
                        stream().
                        map(e -> eventMapper.toResponseDto(e))
                        .toList();
        return eventList;
    }

//    Method to Update Event
    @Override
    @Transactional
    public void updateEvent(EventRequestDto event, UUID id) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found: " + id
                        ));

        eventMapper.updateEntity(event, existingEvent);

    }
}
