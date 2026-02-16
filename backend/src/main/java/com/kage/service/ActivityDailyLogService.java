package com.kage.service;


import com.kage.dto.request.activity.ActivityDailyLogCreateRequest;
import com.kage.dto.request.activity.ActivityDailyLogUpdateRequest;
import com.kage.dto.response.ActivityDailyLogResponse;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ActivityDailyLogService {

    ActivityDailyLogResponse create(
            ActivityDailyLogCreateRequest request,
            Long userId
    );

    @Transactional(readOnly = true)
    ActivityDailyLogResponse getById(Long logId, Long userId);

    @Transactional(readOnly = true)
    List<ActivityDailyLogResponse> getAll(Long userId);

    ActivityDailyLogResponse update(
            ActivityDailyLogUpdateRequest request,
            Long userId
    );

    void deactivate(Long logId, Long userId);

}

