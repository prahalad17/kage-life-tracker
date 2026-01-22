package com.kage.mapper;

import com.kage.dto.request.PillarCreateRequest;
import com.kage.dto.request.PillarUpdateRequest;
import com.kage.dto.response.PillarResponse;
import com.kage.entity.Pillar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PillarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "remarks", ignore = true)
    Pillar toEntity(PillarCreateRequest request);

    PillarResponse toResponse(Pillar pillar);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "remarks", ignore = true)
    void updateEntityFromDto(PillarUpdateRequest dto , @MappingTarget Pillar pillar);

    List<PillarResponse> toResponseList(List<Pillar> pillarsListUser);

}
