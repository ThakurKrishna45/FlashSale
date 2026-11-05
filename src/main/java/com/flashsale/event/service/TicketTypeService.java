package com.flashsale.event.service;

import com.flashsale.event.entity.dto.TicketTypeRequestDto;
import com.flashsale.event.entity.dto.TicketTypeResponseDto;

import java.util.List;
import java.util.UUID;

public interface TicketTypeService {

    TicketTypeResponseDto addTicketType(UUID eventId, TicketTypeRequestDto ticket);

    void updateTicketType(UUID id, TicketTypeRequestDto ticket);

    TicketTypeResponseDto getTicketType(UUID id);

    List<TicketTypeResponseDto> getTicketTypesByEvent(UUID eventId);
}