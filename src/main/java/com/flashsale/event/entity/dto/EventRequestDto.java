package com.flashsale.event.entity.dto;

import com.flashsale.event.entity.EventStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EventRequestDto {

    @NotBlank(message = "Event name is required")
    @Size(max = 200, message = "Event name cannot exceed 200 characters")
    private String name;

    @NotBlank(message = "Venue is required")
    @Size(max = 200, message = "Venue cannot exceed 200 characters")
    private String venue;

    @NotNull(message = "Event start time is required")
    @Future(message = "Event start time must be in the future")
    private Instant startsAt;

    private EventStatus status;
}
