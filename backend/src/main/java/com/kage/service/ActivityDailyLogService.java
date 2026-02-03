package com.kage.service;


import com.kage.dto.request.ActivityDailyLogCreateRequest;
import com.kage.dto.request.ActivityDailyLogUpdateRequest;
import com.kage.dto.response.ActivityDailyLogResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface ActivityDailyLogService {

    List<ActivityDailyLogResponse> getAll();

    ActivityDailyLogResponse getById(Long id);

    ActivityDailyLogResponse create(@Valid ActivityDailyLogCreateRequest request , Long userId);

    ActivityDailyLogResponse update(@Valid ActivityDailyLogUpdateRequest request , Long userId);

    void deactivate(Long id);
}

