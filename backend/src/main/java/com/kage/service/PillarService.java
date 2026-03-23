package com.kage.service;

import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.pillar.PillarCreateRequest;
import com.kage.dto.request.pillar.PillarUpdateRequest;
import com.kage.dto.response.PillarResponse;
import com.kage.entity.Pillar;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;


public interface PillarService {


    Page<PillarResponse> getAll(Long userId, SearchRequestDto request);

    PillarResponse getById(Long id, Long userId);

    PillarResponse create(@Valid PillarCreateRequest request, Long user);

    PillarResponse update(@Valid PillarUpdateRequest request, Long user);

    void deactivate(Long id, Long userId);

    Pillar loadActivePillar(Long templateId);

    Pillar loadOwnedActivePillar(Long id, Long userId);
}

