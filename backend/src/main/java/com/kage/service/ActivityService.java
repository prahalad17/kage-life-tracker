package com.kage.service;


import com.kage.dto.request.ActivityCreateRequest;
import com.kage.dto.request.ActivityUpdateRequest;
import com.kage.dto.response.ActivityResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface ActivityService {

    List<ActivityResponse> getAll();

    ActivityResponse getById(Long id);

    ActivityResponse create(@Valid ActivityCreateRequest request , Long userId);

    ActivityResponse update(@Valid ActivityUpdateRequest request ,Long userId);

    void deactivate(Long id);
}

