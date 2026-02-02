package com.kage.service;


import com.kage.dto.request.ActivityTemplateCreateRequest;
import com.kage.dto.request.ActivityTemplateUpdateRequest;
import com.kage.dto.response.ActivityTemplateResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface ActivityTemplateService {

    List<ActivityTemplateResponse> getAll();

    ActivityTemplateResponse getById(Long id);

    ActivityTemplateResponse create(@Valid ActivityTemplateCreateRequest request);

    ActivityTemplateResponse update( @Valid ActivityTemplateUpdateRequest request);

    void deactivate(Long id);
}

