package com.kage.mapper;

import com.kage.dto.request.ActivityUpdateRequest;
import com.kage.dto.response.ActivityResponse;
import com.kage.entity.Activity;
import com.kage.dto.request.ActivityCreateRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActivityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "remarks", ignore = true)
    @Mapping(source = "activityTemplateId", target = "activityTemplate.id")
    Activity toEntity(ActivityCreateRequest activityCreateRequest);


    @InheritInverseConfiguration(name = "toEntity")
    ActivityResponse toDto(Activity activity);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Activity partialUpdate(ActivityUpdateRequest request, @MappingTarget Activity activity);
}