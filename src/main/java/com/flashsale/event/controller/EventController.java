package com.flashsale.event.controller;

import com.flashsale.event.entity.dto.EventRequestDto;
import com.flashsale.event.entity.dto.EventResponseDto;
import com.flashsale.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/event")
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto event){
        EventResponseDto event1 = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event1);
    }

    @GetMapping("/event/{uuid}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable UUID uuid){
        EventResponseDto event = eventService.getEvent(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    @GetMapping("/event")
    public ResponseEntity<List<EventResponseDto>> getEvent(){
        List<EventResponseDto> allEvent = eventService.getAllEvent();
        return ResponseEntity.status(HttpStatus.OK).body(allEvent);
    }

    @PutMapping("/event/{uuid}")
    public ResponseEntity<Void> updateEvent(@RequestBody EventRequestDto event, @PathVariable UUID uuid) {
        eventService.updateEvent(event, uuid);
        return ResponseEntity.noContent().build();
    }

}
