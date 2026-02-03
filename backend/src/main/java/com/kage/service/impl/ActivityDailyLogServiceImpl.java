package com.kage.service.impl;

import com.kage.dto.request.ActivityDailyLogCreateRequest;
import com.kage.dto.request.ActivityDailyLogUpdateRequest;
import com.kage.dto.response.ActivityDailyLogResponse;
import com.kage.entity.*;
import com.kage.enums.RecordStatus;
import com.kage.enums.UserStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.ActivityDailyLogMapper;
import com.kage.mapper.ActivityMapper;
import com.kage.repository.ActivityRepository;
import com.kage.repository.PillarRepository;
import com.kage.repository.UserRepository;
import com.kage.service.ActivityDailyLogService;
import com.kage.util.SanitizerUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActivityDailyLogServiceImpl implements ActivityDailyLogService {

    private final ActivityDailyLogRepository  activityDailyLogRepository;
    private final ActivityDailyLogMapper activityDailyLogMapper;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    /**
     * Create a new activity
     */
    @Override
    public ActivityDailyLogResponse create(@Valid ActivityDailyLogCreateRequest request , Long userId) {

//        // 1️⃣ Sanitize input
//        String cleanName = SanitizerUtil.clean(request.getName());
//        String cleanDescription = SanitizerUtil.clean(request.getDescription());

//        log.debug("Sanitized activity template name={}", cleanName);

        // 2️⃣ Business validation
//        if (activityDailyLogRepository.existsByNameIgnoreCase(cleanName)) {
//            log.warn("Activity Template already exists with name={}", cleanName);
//            throw new BusinessException("Activity Template with this name already exists");
//        }

        Activity activity = activityRepository.findById(request.getActivityId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid Activity ID"));

        User user = userRepository
                .findByIdAndUserStatusAndStatus(userId, UserStatus.ACTIVE, RecordStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("User not found"));

        ActivityDailyLog activityDailyLog = activityDailyLogMapper.toEntity(request);
        activityDailyLog.setUser(user);
        activityDailyLog.setLogDate(LocalDateTime.now());
        activityDailyLog.setActivity(activity);


        // 4️⃣ Persist
        ActivityDailyLog saved = activityDailyLogRepository.save(activityDailyLog);

//        log.info("Activity Template created successfully with id={}", saved.getId());

        // 5️⃣ Map Entity → DTO
        return activityDailyLogMapper.toDto(saved);
    }

    /**
     * Get activity by id
     */
    @Override
    @Transactional(readOnly = true)
    public ActivityDailyLogResponse getById(Long id) {

        log.debug("Fetching activity with id={}", id);

        ActivityDailyLog activity = activityDailyLogRepository
                .findByIdAndStatus(id, RecordStatus.ACTIVE)
                .orElseThrow(() -> {
                    log.warn("activity not found with id={}", id);
                    return new NotFoundException("activity not found");
                });

        return activityDailyLogMapper.toDto(activity);
    }

    /**
     * Get all active activity
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActivityDailyLogResponse> getAll() {

        log.debug("Fetching all active activity");

        return activityDailyLogRepository.findByStatus(RecordStatus.ACTIVE)
                .stream()
                .map(activityDailyLogMapper::toDto)
                .toList();
    }

    /**
     * Update activity
     */
    @Override
    public ActivityDailyLogResponse update(@Valid  ActivityDailyLogUpdateRequest request , Long userId) {

        log.debug("Updating activity with id={}", request.getActivityId());

        ActivityDailyLog activityDailyLog = activityDailyLogRepository
                .findByIdAndStatus(request.getActivityId(), RecordStatus.ACTIVE)
                .orElseThrow(() -> {
                    log.warn("Cannot update. activity log not found with id={}", request.getActivityId());
                    return new NotFoundException("activity log not found");
                });

        // 1️⃣ Sanitize inputs
//        String cleanName = SanitizerUtil.clean(request.getName());
//        String cleanDescription = SanitizerUtil.clean(request.getDescription());

        // 2️⃣ Business rule: unique name (only if changed)
//        if (!activity.getName().equalsIgnoreCase(cleanName)
//                && activityDailyLogRepository.existsByNameIgnoreCase(cleanName)) {
//
//            log.warn("Duplicate activity name during update: {}", cleanName);
//            throw new BusinessException("Another activity already uses this name");
//        }

        // 3️⃣ Map updates (MapStruct)
        activityDailyLogMapper.partialUpdate(request, activityDailyLog);
//        activity.setName(cleanName);
//        activity.setDescription(cleanDescription);

        // 4️⃣ Save
        ActivityDailyLog updated = activityDailyLogRepository.save(activityDailyLog);

        log.info("activity updated successfully with id={}", updated.getId());

        return activityDailyLogMapper.toDto(updated);
    }

    /**
     * Soft delete (deactivate)
     */
    @Override
    public void deactivate(Long id) {

        log.debug("Deactivating activity with id={}", id);

        ActivityDailyLog activityDailyLog = activityDailyLogRepository
                .findByIdAndStatus(id, RecordStatus.ACTIVE)
                .orElseThrow(() -> {
                    log.warn("Cannot deactivate. activity not found with id={}", id);
                    return new NotFoundException("activity not found");
                });

        activityDailyLog.setStatus(RecordStatus.INACTIVE);
        activityDailyLogRepository.save(activityDailyLog);

        log.info("activity log deactivated successfully with id={}", id);
    }
}
