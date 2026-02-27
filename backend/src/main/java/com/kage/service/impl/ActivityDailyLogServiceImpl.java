package com.kage.service.impl;

import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.activity.ActivityDailyLogCreateRequest;
import com.kage.dto.request.activity.ActivityDailyLogUpdateRequest;
import com.kage.dto.response.ActivityDailyLogResponse;
import com.kage.entity.Activity;
import com.kage.entity.ActivityDailyLog;
import com.kage.entity.User;
import com.kage.enums.RecordStatus;
import com.kage.exception.NotFoundException;
import com.kage.mapper.ActivityDailyLogMapper;
import com.kage.repository.ActivityDailyLogRepository;
import com.kage.repository.ActivityRepository;
import com.kage.service.ActivityDailyLogService;
import com.kage.service.UserService;
import com.kage.util.PageableBuilderUtil;
import com.kage.util.SpecificationBuilderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActivityDailyLogServiceImpl implements ActivityDailyLogService {

    private final ActivityDailyLogRepository logRepository;
    private final ActivityRepository activityRepository;
    private final UserService userService;
    private final ActivityDailyLogMapper mapper;

    /**
     * Create a new activity execution log (log now)
     */
    @Override
    public ActivityDailyLogResponse create(
            ActivityDailyLogCreateRequest request,
            Long userId
    ) {

        log.debug("Creating activity log for userId={}, activityId={}",
                userId, request.getActivityId());

        User user = userService.loadActiveUser(userId);

        Activity activity = activityRepository
                .findByIdAndStatus(request.getActivityId(), RecordStatus.ACTIVE)
                .orElseThrow(() ->
                        new NotFoundException("Activity not found"));

        // Ownership validation (extra safety at service boundary)
        if (!activity.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User does not own this activity");
        }

        ActivityDailyLog log = ActivityDailyLog.logNow(
                activity,
                user,
                request.getActualValue(),
                request.getCompleted(),
                request.getNotes()
        );

        logRepository.save(log);

//        log.info("ActivityDailyLog created with id={}", log.getId());

        return mapper.toDto(log);
    }

    /**
     * Get activity log by id (ownership enforced)
     */
    @Transactional(readOnly = true)
    @Override
    public ActivityDailyLogResponse getById(Long logId, Long userId) {

        ActivityDailyLog log = loadOwnedLog(logId, userId);
        return mapper.toDto(log);
    }

    /**
     * Get all active logs for the current user
     */
    @Transactional(readOnly = true)
    @Override
    public List<ActivityDailyLogResponse> getAll(Long userId) {

        return logRepository
                .findByUserIdAndStatus(userId, RecordStatus.ACTIVE)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Page<ActivityDailyLogResponse> search(Long id, SearchRequestDto request) {

        Pageable pageable = PageableBuilderUtil.build(request);

        Specification<ActivityDailyLog> specification =
                SpecificationBuilderUtil.build(request);


    }

    @Override
    public List<ActivityDailyLogResponse> getToDo(Long userId, LocalDate date) {

        return logRepository
                .findByUserIdAndLogDateAndCompletedAndStatus(userId, date, false, RecordStatus.ACTIVE)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ActivityDailyLogResponse> getCompleted(Long userId, LocalDate date) {

        return logRepository
                .findByUserIdAndLogDateAndCompletedAndStatus(userId, date, true, RecordStatus.ACTIVE)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Update an existing activity log
     */
    @Override
    public ActivityDailyLogResponse update(
            ActivityDailyLogUpdateRequest request,
            Long userId
    ) {

//        log.debug("Updating activity log id={}", request.getLogId());

        ActivityDailyLog log =
                loadOwnedLog(request.getLogId(), userId);

        log.updateTracking(
                request.getActualValue(),
                request.getCompleted(),
                request.getNotes()
        );

//        log.info("ActivityDailyLog updated with id={}", log.getId());

        return mapper.toDto(log);
    }

    /**
     * Soft delete an activity log
     */
    @Override
    public void deactivate(Long logId, Long userId) {

        log.debug("Deactivating activity log id={}", logId);

        ActivityDailyLog log = loadOwnedLog(logId, userId);
        log.deactivate();

//        log.info("ActivityDailyLog deactivated with id={}", logId);
    }



    /* ----------------------------------------------------
       Internal helpers
       ---------------------------------------------------- */

    @Transactional(readOnly = true)
    protected ActivityDailyLog loadOwnedLog(Long logId, Long userId) {

        ActivityDailyLog log = logRepository
                .findByIdAndStatus(logId, RecordStatus.ACTIVE)
                .orElseThrow(() ->
                        new NotFoundException("Activity log not found"));

        if (!log.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User does not own this activity log");
        }

        return log;
    }
}
