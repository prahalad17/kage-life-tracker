package com.kage.service;

import com.kage.dto.request.PillarCreateRequest;
import com.kage.dto.request.PillarUpdateRequest;
import com.kage.dto.response.PillarResponse;
import jakarta.validation.Valid;

import java.util.List;


public interface PillarService {


    List<PillarResponse> getAll();

    PillarResponse getById(Long id);

    PillarResponse create(@Valid PillarCreateRequest request);

    PillarResponse update(Long id, @Valid PillarUpdateRequest request);

    void deactivate(Long id);
}

