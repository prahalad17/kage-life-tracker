package com.kage.mapper;

import com.kage.dto.request.pillar.PillarTemplateCreateRequest;
import com.kage.dto.request.pillar.PillarTemplateUpdateRequest;
import com.kage.dto.response.PillarTemplateResponse;
import com.kage.entity.PillarTemplate;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PillarTemplateMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "remarks", ignore = true)
//    PillarTemplate toEntity(PillarTemplateCreateRequest request);
//
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "remarks", ignore = true)
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    PillarTemplate updateEntityFromDto(PillarTemplateUpdateRequest dto , @MappingTarget PillarTemplate pillarTemplate);

    PillarTemplateResponse toResponse(PillarTemplate pillarTemplate);

    List<PillarTemplateResponse> toResponseList(List<PillarTemplate> pillarTemplates);




}
