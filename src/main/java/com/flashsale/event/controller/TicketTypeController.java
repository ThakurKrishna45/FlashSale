package com.flashsale.event.controller;

import com.flashsale.event.entity.dto.TicketTypeRequestDto;
import com.flashsale.event.entity.dto.TicketTypeResponseDto;
import com.flashsale.event.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/ticket-types")
@RequiredArgsConstructor
public class TicketTypeController {
    final private TicketTypeService ticketTypeService;

    @PostMapping("/{eventId}")
    public ResponseEntity<TicketTypeResponseDto> addTicketType(@PathVariable UUID eventId, @RequestBody TicketTypeRequestDto ticket){
        TicketTypeResponseDto ticketTypeResponseDto = ticketTypeService.addTicketType(eventId, ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketTypeResponseDto);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<TicketTypeResponseDto>> getAllTicketsOfEvent(@PathVariable UUID eventId){
        List<TicketTypeResponseDto> ticketTypesByEvent = ticketTypeService.getTicketTypesByEvent(eventId);
        return ResponseEntity.ok(ticketTypesByEvent);
    }

    @GetMapping("/{ticketTypeId}")
    public ResponseEntity<TicketTypeResponseDto> getTicketType(@PathVariable UUID ticketTypeId){
        TicketTypeResponseDto ticketType = ticketTypeService.getTicketType(ticketTypeId);
        return ResponseEntity.ok(ticketType);
    }

    @PutMapping("/{ticketTypeId}")
    public ResponseEntity<Void> updateTicketType(@PathVariable UUID ticketTypeId,
                                                 @RequestBody TicketTypeRequestDto ticket){
        ticketTypeService.updateTicketType(ticketTypeId,ticket);
        return ResponseEntity.noContent().build();
    }
}
