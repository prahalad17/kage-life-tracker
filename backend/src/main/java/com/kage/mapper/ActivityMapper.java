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
    @Mapping(target = "nature", source = "activityNature")
    @Mapping(target = "trackingType", source = "defaultTrackingType")
    Activity toEntity(ActivityCreateRequest activityCreateRequest);


    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(target = "pillar", source = "pillar.name")
    @Mapping(target = "activityNature", source = "nature")
    @Mapping(target = "activityId", source = "id")
    ActivityResponse toDto(Activity activity);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "pillar.name", source = "pillar")
    @Mapping(target = "nature", source = "activityNature")
    Activity partialUpdate(ActivityUpdateRequest request, @MappingTarget Activity activity);
}