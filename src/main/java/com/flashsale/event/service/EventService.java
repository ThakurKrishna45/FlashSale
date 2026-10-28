
package com.flashsale.event.service;

import com.flashsale.event.entity.dto.EventRequestDto;
import com.flashsale.event.entity.dto.EventResponseDto;

import java.util.List;
import java.util.UUID;

public interface EventService {

    EventResponseDto createEvent(EventRequestDto event);

    EventResponseDto getEvent(UUID id);
    List<EventResponseDto> getAllEvent();
    void updateEvent(EventRequestDto event, UUID id);
}
