package com.kage.mapper;

import com.kage.dto.response.ActionEntryResponse;
import com.kage.entity.ActionEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActionEntryMapper {

    @Mapping(target = "actionEntryId", source = "id")
    @Mapping(target = "dayEntryId", source = "dayEntry.id")
    @Mapping(target = "activityId", source = "activity.name")
    @Mapping(target = "pillarId", source = "pillar.id")
    ActionEntryResponse toDto(ActionEntry actionEntry);

}