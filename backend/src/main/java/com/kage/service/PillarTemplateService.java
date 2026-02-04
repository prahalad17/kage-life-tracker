package com.kage.service;

import com.kage.dto.request.pillar.PillarTemplateCreateRequest;
import com.kage.dto.request.pillar.PillarTemplateUpdateRequest;
import com.kage.dto.response.PillarTemplateResponse;
import com.kage.entity.PillarTemplate;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface PillarTemplateService {


    List<PillarTemplateResponse> getAll();

    PillarTemplateResponse getById(Long id);

    PillarTemplateResponse create(@Valid PillarTemplateCreateRequest request);

    PillarTemplateResponse update(PillarTemplateUpdateRequest request);

    void deactivate(Long id);

    PillarTemplate loadActiveTemplate(Long templateId);
}

