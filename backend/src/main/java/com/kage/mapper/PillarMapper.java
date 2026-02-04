package com.kage.mapper;

import com.kage.dto.request.pillar.PillarCreateRequest;
import com.kage.dto.request.pillar.PillarUpdateRequest;
import com.kage.dto.response.PillarResponse;
import com.kage.entity.Pillar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PillarMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "remarks", ignore = true)
//    Pillar toEntity(PillarCreateRequest request);

    @Mapping(target = "templatePillarId",source = "pillarTemplate.id")
    @Mapping(target = "pillar",source = "pillarTemplate.name")
    PillarResponse toResponse(Pillar pillar);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "remarks", ignore = true)
//    void updateEntityFromDto(PillarUpdateRequest dto , @MappingTarget Pillar pillar);

//    @Mapping(target = "templatePillarId",source = "pillarTemplate.id")
    List<PillarResponse> toResponseList(List<Pillar> pillarsListUser);

}
