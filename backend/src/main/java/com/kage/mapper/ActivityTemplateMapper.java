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
    @Mapping(target = "nature", source = "activityNature")
    ActivityTemplate toEntity(ActivityTemplateCreateRequest request);

    @InheritInverseConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "pillarTemplate.name", source = "pillar")
    @Mapping(target = "nature", source = "activityNature")
    ActivityTemplate partialUpdate(ActivityTemplateUpdateRequest activityTemplateDto, @MappingTarget ActivityTemplate activityTemplate);

    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(target = "pillar", source = "pillarTemplate.name")
    @Mapping(target = "activityNature", source = "nature")
    @Mapping(target = "activityId", source = "id")
    ActivityTemplateResponse toDto(ActivityTemplate activityTemplate);


    List<ActivityTemplateResponse> toResponseList(List<ActivityTemplate> activityTemplates);


}