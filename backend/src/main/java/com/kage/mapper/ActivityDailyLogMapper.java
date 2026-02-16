package com.kage.mapper;

import com.kage.dto.request.activity.ActivityDailyLogCreateRequest;
import com.kage.dto.request.activity.ActivityDailyLogUpdateRequest;
import com.kage.dto.response.ActivityDailyLogResponse;
import com.kage.entity.ActivityDailyLog;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActivityDailyLogMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "remarks", ignore = true)
//    ActivityDailyLog toEntity(ActivityDailyLogCreateRequest req);


    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(target = "activityId" ,source = "activity.id")
    @Mapping(target = "activityName" ,source = "activity.name")
    @Mapping(target = "activityDailyLogId" , source = "id")
    ActivityDailyLogResponse toDto(ActivityDailyLog activityDailyLog);

//    @InheritConfiguration(name = "toEntity")
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    ActivityDailyLog partialUpdate(ActivityDailyLogUpdateRequest request, @MappingTarget ActivityDailyLog activity);
}