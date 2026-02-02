package com.kage.service.impl;

import com.kage.dto.request.ActivityCreateRequest;
import com.kage.dto.request.ActivityUpdateRequest;
import com.kage.dto.response.ActivityResponse;
import com.kage.entity.*;
import com.kage.enums.RecordStatus;
import com.kage.enums.UserStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.ActivityMapper;
import com.kage.repository.ActivityRepository;
import com.kage.repository.PillarRepository;
import com.kage.repository.UserRepository;
import com.kage.service.ActivityService;
import com.kage.util.SanitizerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final PillarRepository pillarRepository;
    private final UserRepository userRepository;

    /**
     * Create a new activity
     */
    @Override
    public ActivityResponse create(ActivityCreateRequest request , Long userId) {

        // 1️⃣ Sanitize input
        String cleanName = SanitizerUtil.clean(request.getName());
        String cleanDescription = SanitizerUtil.clean(request.getDescription());

        log.debug("Sanitized activity template name={}", cleanName);

        // 2️⃣ Business validation
        if (activityRepository.existsByNameIgnoreCase(cleanName)) {
            log.warn("Activity Template already exists with name={}", cleanName);
            throw new BusinessException("Activity Template with this name already exists");
        }

        Pillar pillar = pillarRepository
                .findById(request.getPillarId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid PillarTemplate ID"));

        User user = userRepository
                .findByIdAndUserStatusAndStatus(userId, UserStatus.ACTIVE, RecordStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // 3️⃣ Map DTO → Entity
        Activity activity = activityMapper.toEntity(request);
        activity.setName(cleanName);
        activity.setDescription(cleanDescription);
        activity.setPillar(pillar);
        activity.setUser(user);
//        pillar.setActive(true);

        // 4️⃣ Persist
        Activity saved = activityRepository.save(activity);

        log.info("Activity Template created successfully with id={}", saved.getId());

        // 5️⃣ Map Entity → DTO
        return activityMapper.toDto(saved);
    }

    /**
     * Get activity by id
     */
    @Override
    @Transactional(readOnly = true)
    public ActivityResponse getById(Long id) {

        log.debug("Fetching activity with id={}", id);

        Activity activity = activityRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    log.warn("activity not found with id={}", id);
                    return new NotFoundException("activity not found");
                });

        return activityMapper.toDto(activity);
    }

    /**
     * Get all active activity
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActivityResponse> getAll() {

        log.debug("Fetching all active activity");

        return activityRepository.findByActiveTrue()
                .stream()
                .map(activityMapper::toDto)
                .toList();
    }

    /**
     * Update activity
     */
    @Override
    public ActivityResponse update( ActivityUpdateRequest request , Long userId) {

        log.debug("Updating activity with id={}", request.getActivityId());

        Activity activity = activityRepository
                .findByIdAndActiveTrue(request.getActivityId())
                .orElseThrow(() -> {
                    log.warn("Cannot update. activity not found with id={}", request.getActivityId());
                    return new NotFoundException("activity not found");
                });

        // 1️⃣ Sanitize inputs
        String cleanName = SanitizerUtil.clean(request.getName());
//        String cleanDescription = SanitizerUtil.clean(request.getDescription());

        // 2️⃣ Business rule: unique name (only if changed)
        if (!activity.getName().equalsIgnoreCase(cleanName)
                && activityRepository.existsByNameIgnoreCase(cleanName)) {

            log.warn("Duplicate activity name during update: {}", cleanName);
            throw new BusinessException("Another activity already uses this name");
        }

        // 3️⃣ Map updates (MapStruct)
        activityMapper.partialUpdate(request, activity);
        activity.setName(cleanName);
//        activity.setDescription(cleanDescription);

        // 4️⃣ Save
        Activity updated = activityRepository.save(activity);

        log.info("activity updated successfully with id={}", updated.getId());

        return activityMapper.toDto(updated);
    }

    /**
     * Soft delete (deactivate)
     */
    @Override
    public void deactivate(Long id) {

        log.debug("Deactivating activity with id={}", id);

        Activity activity = activityRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    log.warn("Cannot deactivate. activity not found with id={}", id);
                    return new NotFoundException("activity not found");
                });

        activity.setActive(false);
        activityRepository.save(activity);

        log.info("activity deactivated successfully with id={}", id);
    }
}
