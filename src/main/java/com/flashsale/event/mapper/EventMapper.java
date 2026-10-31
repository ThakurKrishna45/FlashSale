package com.flashsale.event.mapper;

import com.flashsale.event.entity.Event;
import com.flashsale.event.entity.dto.EventRequestDto;
import com.flashsale.event.entity.dto.EventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = CentralMapperConfig.class)
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ticketTypes", ignore = true)
    Event toEntity(EventRequestDto dto);

    EventResponseDto toResponseDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ticketTypes", ignore = true)
    void updateEntity(
            EventRequestDto dto,
            @MappingTarget Event event
    );
}