package com.kage.mapper;

import com.kage.dto.response.DayEntryResponse;
import com.kage.entity.DayEntry;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DayEntryMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "remarks", ignore = true)
//    ActivityDailyLog toEntity(ActivityDailyLogCreateRequest req);


    DayEntryResponse toDto(DayEntry dayEntry);

//    @InheritConfiguration(name = "toEntity")
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    ActivityDailyLog partialUpdate(ActivityDailyLogUpdateRequest request, @MappingTarget ActivityDailyLog activity);
}