package com.kage.service;

import com.kage.dto.request.PillarTemplateCreateRequest;
import com.kage.dto.request.PillarTemplateUpdateRequest;
import com.kage.dto.response.PillarTemplateResponse;
import jakarta.validation.Valid;

import java.util.List;


public interface PillarTemplateService {


    List<PillarTemplateResponse> getAll();

    PillarTemplateResponse getById(Long id);

    PillarTemplateResponse create(@Valid PillarTemplateCreateRequest request);

    PillarTemplateResponse update(Long id, @Valid PillarTemplateUpdateRequest request);

    void deactivate(Long id);
}

