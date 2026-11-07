package com.flashsale.event.mapper;

import com.flashsale.event.entity.TicketType;
import com.flashsale.event.entity.dto.TicketTypeRequestDto;
import com.flashsale.event.entity.dto.TicketTypeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = CentralMapperConfig.class)
public interface TicketTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    TicketType toTicketType(TicketTypeRequestDto dto);

    @Mapping(target = "eventId", source = "event.id")
    TicketTypeResponseDto toResponseDto(TicketType ticketType);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    void updateTicketType(
            TicketTypeRequestDto dto,
            @MappingTarget TicketType ticketType
    );
}