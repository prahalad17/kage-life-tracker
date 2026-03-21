package com.kage.mapper;

import com.kage.dto.response.ActionPlanResponse;
import com.kage.entity.ActionPlan;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActionPlanMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "remarks", ignore = true)
//    ActivityDailyLog toEntity(ActivityDailyLogCreateRequest req);


    ActionPlanResponse toDto(ActionPlan actionPlan);

//    @InheritConfiguration(name = "toEntity")
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    ActivityDailyLog partialUpdate(ActivityDailyLogUpdateRequest request, @MappingTarget ActivityDailyLog activity);
}