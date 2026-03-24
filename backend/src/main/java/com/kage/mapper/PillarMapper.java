package com.kage.mapper;

import com.kage.dto.response.PillarResponse;
import com.kage.entity.Pillar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PillarMapper {

    @Mapping(target = "pillarTemplateId", source = "pillarTemplate.id")
    @Mapping(target = "pillarId", source = "id")
    PillarResponse toDto(Pillar pillar);


}
