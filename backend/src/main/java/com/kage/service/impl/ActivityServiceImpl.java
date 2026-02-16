package com.kage.service.impl;

import com.kage.dto.request.activity.ActivityCreateRequest;
import com.kage.dto.request.activity.ActivityUpdateRequest;
import com.kage.dto.response.ActivityResponse;
import com.kage.entity.*;
import com.kage.enums.RecordStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.ActivityMapper;
import com.kage.repository.ActivityRepository;
import com.kage.service.ActivityService;
import com.kage.service.PillarService;
import com.kage.service.UserService;
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
    private final UserService userService;
    private final PillarService pillarService;
    private final ActivityMapper activityMapper;

    /**
     * Create a new activity + schedule (atomic)
     */
    @Override
    public ActivityResponse create(ActivityCreateRequest request, Long userId) {

        log.debug("Creating activity for userId={}", userId);

        User user = userService.loadActiveUser(userId);
        Pillar pillar = pillarService.loadActivePillar(request.getPillarId());

        // Uniqueness: user + pillar + name + ACTIVE
        if (activityRepository.existsByUserAndPillarAndNameIgnoreCaseAndStatus(
                user,
                pillar,
                request.getActivityName(),
                RecordStatus.ACTIVE)) {

            throw new BusinessException(
                    "Activity with this name already exists in this pillar"
            );
        }

        // Create aggregate root
        Activity activity = Activity.create(
                user,
                pillar,
                request.getActivityName(),
                request.getNature(),
                request.getTrackingType(),
                request.getUnit(),
                request.getDescription()
        );

        // Create and attach dependent entity
        ActivitySchedule schedule = ActivitySchedule.create(
                activity,
                request.getScheduleType(),
                request.getDays()
        );

        activity.attachSchedule(schedule);

        // One save – cascades schedule
        activityRepository.save(activity);

        log.info("Activity created with id={}", activity.getId());

        return activityMapper.toDto(activity);
    }

    /**
     * Get activity by id
     */
    @Override
    @Transactional(readOnly = true)
    public ActivityResponse getById(Long id, Long userId) {

        Activity activity = loadOwnedActiveActivity(id, userId);
        return activityMapper.toDto(activity);
    }

    /**
     * Get all active activities for user
     */
    @Transactional(readOnly = true)
    @Override
    public List<ActivityResponse> getAll(Long userId) {

        return activityRepository
                .findByUserIdAndStatus(userId, RecordStatus.ACTIVE)
                .stream()
                .map(activityMapper::toDto)
                .toList();
    }

    /**
     * Update activity + schedule
     */
    @Override
    public ActivityResponse update(ActivityUpdateRequest request, Long userId) {

        log.debug("Updating activity id={}", request.getActivityId());

        Activity activity =
                loadOwnedActiveActivity(request.getActivityId(), userId);

        // Rename (uniqueness only if changed)
        if (!activity.getName().equalsIgnoreCase(request.getActivityName())
                && activityRepository.existsByUserAndPillarAndNameIgnoreCaseAndStatus(
                activity.getUser(),
                activity.getPillar(),
                request.getActivityName(),
                RecordStatus.ACTIVE)) {

            throw new BusinessException(
                    "Another activity with this name already exists in this pillar"
            );
        }

        activity.rename(request.getActivityName());
        activity.updateDescription(request.getDescription());
        activity.updateNature(request.getNature());

        activity.changeTracking(
                request.getTrackingType(),
                request.getUnit()
        );

        activity.updateSchedule(
                request.getScheduleType(),
                request.getDays()
        );

        log.info("Activity updated with id={}", activity.getId());

        // No save() — dirty checking
        return activityMapper.toDto(activity);
    }

    /**
     * Soft delete activity (aggregate root)
     */
    @Override
    public void deactivate(Long activityId, Long userId) {

        log.debug("Deactivating activity id={}", activityId);

        Activity activity = loadOwnedActiveActivity(activityId, userId);
        activity.deactivateSchedule();
        activity.deactivate();

        log.info("Activity deactivated with id={}", activityId);
    }

    /* -----------------------------------------------------
       Internal helpers (aggregate loaders)
       ----------------------------------------------------- */

    @Transactional(readOnly = true)
    protected Activity loadOwnedActiveActivity(Long id, Long userId) {

        Activity activity = activityRepository
                .findByIdAndStatus(id, RecordStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Activity not found"));

        if (!activity.getUser().getId().equals(userId)) {
            throw new BusinessException("User does not own this activity");
        }

        return activity;
    }

}

