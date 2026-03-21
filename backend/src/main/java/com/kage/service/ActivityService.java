package com.kage.service;


import com.kage.dto.request.activity.ActivityCreateRequest;
import com.kage.dto.request.activity.ActivityUpdateRequest;
import com.kage.dto.response.ActivityResponse;
import com.kage.entity.Activity;
import jakarta.validation.Valid;

import java.util.List;

public interface ActivityService {

    ActivityResponse getById(Long id, Long userId);

    ActivityResponse create(@Valid ActivityCreateRequest request, Long userId);

    List<ActivityResponse> getAll(Long userId);

    ActivityResponse update(@Valid ActivityUpdateRequest request, Long userId);

    void deactivate(Long activityId, Long userId);

    /* -----------------------------------------------------
           Internal helpers (aggregate loaders)
           ----------------------------------------------------- */
    Activity loadOwnedActiveActivity(Long id, Long userId);
}

