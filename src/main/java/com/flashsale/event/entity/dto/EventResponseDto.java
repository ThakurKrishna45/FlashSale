package com.flashsale.event.entity.dto;

import com.flashsale.event.entity.EventStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class EventResponseDto {

    private UUID id;
    private String name;
    private String venue;
    private Instant startsAt;
    private EventStatus status;
    private Instant createdAt;
}
