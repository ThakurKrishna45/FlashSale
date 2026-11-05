package com.flashsale.event.service.Impementation;

import com.flashsale.event.entity.Event;
import com.flashsale.event.entity.TicketType;
import com.flashsale.event.entity.dto.TicketTypeRequestDto;
import com.flashsale.event.entity.dto.TicketTypeResponseDto;
import com.flashsale.event.exception.ResourceNotFoundException;
import com.flashsale.event.mapper.TicketTypeMapper;
import com.flashsale.event.repository.EventRepository;
import com.flashsale.event.repository.TicketTypeRepository;
import com.flashsale.event.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTyperServiceImpl implements TicketTypeService {

    final private TicketTypeRepository ticketTypeRepository;
    final private EventRepository eventRepository;
    final private TicketTypeMapper mapper;

    @Override
    @Transactional
    public TicketTypeResponseDto addTicketType(UUID eventId, TicketTypeRequestDto ticket) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException(
                "Event not found :" + eventId));

        TicketType ticketType = mapper.toTicketType(ticket);
        event.addTicketType(ticketType);

        TicketType saved = ticketTypeRepository.save(ticketType);
        return mapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void updateTicketType(UUID id, TicketTypeRequestDto ticket) {
        TicketType ticketType = ticketTypeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ticket Type not found of id: " + id));
        mapper.updateTicketType(ticket, ticketType);
    }

    @Override
    public TicketTypeResponseDto getTicketType(UUID id) {
        TicketType ticketType = ticketTypeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ticket Type not found of id: " + id));
        return mapper.toResponseDto(ticketType);
    }

    @Override
    public List<TicketTypeResponseDto> getTicketTypesByEvent(UUID eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException(
                "Event not found :" + eventId));
        List<TicketType> ticketTypes = event.getTicketTypes();
        return ticketTypes.stream().map(t->mapper.toResponseDto(t)).toList();
    }
}
