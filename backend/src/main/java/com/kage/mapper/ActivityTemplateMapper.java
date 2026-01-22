package com.kage.mapper;

import com.kage.dto.request.ActivityTemplateCreateRequest;
import com.kage.dto.request.ActivityTemplateUpdateRequest;
import com.kage.dto.response.ActivityTemplateResponse;
import com.kage.entity.ActivityTemplate;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActivityTemplateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "remarks", ignore = true)
    ActivityTemplate toEntity(ActivityTemplateCreateRequest request);

    @InheritInverseConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ActivityTemplate partialUpdate(ActivityTemplateUpdateRequest activityTemplateDto, @MappingTarget ActivityTemplate activityTemplate);

    ActivityTemplateResponse toDto(ActivityTemplate activityTemplate);

    List<ActivityTemplateResponse> toResponseList(List<ActivityTemplate> activityTemplates);


}