package com.kage.service;


import com.kage.dto.request.activity.ActivityCreateRequest;
import com.kage.dto.request.activity.ActivityUpdateRequest;
import com.kage.dto.response.ActivityResponse;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActivityService {

    ActivityResponse getById(Long id, Long userId);

    ActivityResponse create(@Valid ActivityCreateRequest request , Long userId);

    List<ActivityResponse> getAll(Long userId);

    ActivityResponse update(@Valid ActivityUpdateRequest request , Long userId);

    void deactivate(Long activityId, Long userId);
}

