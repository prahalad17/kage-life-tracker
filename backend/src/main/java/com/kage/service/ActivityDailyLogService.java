package com.kage.service;


import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.activity.ActivityDailyLogCreateRequest;
import com.kage.dto.request.activity.ActivityDailyLogUpdateRequest;
import com.kage.dto.response.ActivityDailyLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
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

    @Transactional(readOnly = true)
    List<ActivityDailyLogResponse> getToDo(Long userId , LocalDate date);

    @Transactional(readOnly = true)
    List<ActivityDailyLogResponse> getCompleted(Long userId , LocalDate date);

    ActivityDailyLogResponse update(
            ActivityDailyLogUpdateRequest request,
            Long userId
    );

    void deactivate(Long logId, Long userId);

    Page<ActivityDailyLogResponse> search(Long id, SearchRequestDto request);
}

