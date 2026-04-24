package com.kage.service.impl;

import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.activity.ActivityCreateRequest;
import com.kage.dto.request.activity.ActivityUpdateRequest;
import com.kage.dto.response.ActivityResponse;
import com.kage.entity.Activity;
import com.kage.entity.ActivitySchedule;
import com.kage.entity.Pillar;
import com.kage.entity.User;
import com.kage.enums.RecordStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.ActivityMapper;
import com.kage.repository.ActivityRepository;
import com.kage.service.ActivityService;
import com.kage.service.PillarService;
import com.kage.service.UserService;
import com.kage.util.PageableBuilderUtil;
import com.kage.util.SpecificationBuilderUtil;
import com.kage.util.UserSpecificationBuilderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

        Pillar pillar = null;

        if (request.pillarId() != null) {
            pillar = pillarService.loadActivePillar(request.pillarId());

            // Uniqueness: user + pillar + name + ACTIVE
            if (activityRepository.existsByUserAndPillarAndActivityNameIgnoreCaseAndStatus(
                    user,
                    pillar,
                    request.activityName(),
                    RecordStatus.ACTIVE)) {

                throw new BusinessException(
                        "Activity with this name already exists in this pillar"
                );
            }
        } else {

            // Uniqueness: user + name + ACTIVE
            if (activityRepository.existsByUserAndActivityNameIgnoreCaseAndStatus(
                    user,
                    request.activityName(),
                    RecordStatus.ACTIVE)) {

                throw new BusinessException(
                        "Activity with this name already exists for the user, Consider Adding To a pillar"
                );
            }
        }


        Activity activity = Activity.create(
                user,
                request.activityName(),
                request.activityType(),
                request.activityNature(),
                request.activityTrackingType()
        );

        // Create and attach dependent entity
        ActivitySchedule schedule = ActivitySchedule.create(
                activity,
                request.activityScheduleType(),
                request.days()
        );
        activity.attachSchedule(schedule);

        activity.setActivityDescription(request.activityDescription());

        if (pillar != null) {
            activity.addPillar(pillar);
        }

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
    public Page<ActivityResponse> getAll(Long userId, SearchRequestDto request) {

        Pageable pageable = PageableBuilderUtil.build(request);

        Specification<Activity> dynamicSpec =
                SpecificationBuilderUtil.build(request);

        Specification<Activity> mandatorySpec =
                UserSpecificationBuilderUtil.build(userId);

        Specification<Activity> finalSpec =
                mandatorySpec.and(dynamicSpec);

        return activityRepository.findAll(finalSpec, pageable)
                .map(activityMapper::toDto);
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

        log.debug("Updating activity id={}", request.activityId());

        Activity activity =
                loadOwnedActiveActivity(request.activityId(), userId);

        // Rename (uniqueness only if changed)
        if (!activity.getActivityName().equalsIgnoreCase(request.activityName())
                && activityRepository.existsByUserAndPillarAndActivityNameIgnoreCaseAndStatus(
                activity.getUser(),
                activity.getPillar(),
                request.activityName(),
                RecordStatus.ACTIVE)) {

            throw new BusinessException(
                    "Another activity with this name already exists in this pillar"
            );
        }

        activity.rename(request.activityName());
        activity.updateDescription(request.activityDescription());
        activity.updateNature(request.activityNature());

        activity.changeTracking(
                request.activityTrackingType()
        );


        if (request.activityScheduleType() != null) {
        if(activity.getSchedule() == null) {
            // Create and attach dependent entity
            ActivitySchedule schedule = ActivitySchedule.create(
                    activity,
                    request.activityScheduleType(),
                    request.days()
            );
            activity.attachSchedule(schedule);
        }else{
            activity.updateSchedule(request.activityScheduleType(), request.days());
        }

        // Update schedule with proper handling of schedule type and days


        }

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
    @Override
    public Activity loadOwnedActiveActivity(Long id, Long userId) {

        Activity activity = activityRepository
                .findByIdAndStatus(id, RecordStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Activity not found"));

        if (!activity.getUser().getId().equals(userId)) {
            throw new BusinessException("User does not own this activity");
        }

        return activity;
    }

}

