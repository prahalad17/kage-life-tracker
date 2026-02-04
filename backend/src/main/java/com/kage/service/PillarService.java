package com.kage.service;

import com.kage.dto.request.pillar.PillarCreateRequest;
import com.kage.dto.request.pillar.PillarUpdateRequest;
import com.kage.dto.response.PillarResponse;
import com.kage.entity.Pillar;
import com.kage.entity.PillarTemplate;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface PillarService {


    List<PillarResponse> getAll(Long userId);

    PillarResponse getById(Long id,Long userId);

    PillarResponse create(@Valid PillarCreateRequest request, Long user);

    PillarResponse update( @Valid PillarUpdateRequest request,Long user);

    void deactivate(Long id , Long userId);

    Pillar loadActivePillar(Long templateId);
}

