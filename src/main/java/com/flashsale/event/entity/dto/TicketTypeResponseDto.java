package com.flashsale.event.entity.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class TicketTypeResponseDto {

    private UUID id;
    private UUID eventId;
    private String name;
    private BigDecimal price;
    private String currency;
    private Integer totalQuantity;
}
