package com.kage.service.impl;

import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.action.ActionEntryCreateRequest;
import com.kage.dto.request.action.ActionEntryUpdateRequest;
import com.kage.dto.response.ActionEntryResponse;
import com.kage.entity.*;
import com.kage.enums.ActionEntryStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.RecordStatus;
import com.kage.enums.TrackingType;
import com.kage.exception.NotFoundException;
import com.kage.mapper.ActionEntryMapper;
import com.kage.repository.ActionEntryRepository;
import com.kage.service.*;
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

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActionEntryServiceImpl implements ActionEntryService {

    private final ActionEntryRepository actionEntryRepository;
    private final ActionEntryMapper actionEntryMapper;
    private final UserService userService;
    private final DayEntryService dayEntryService;
    private final ActivityService activityService;
    private final PillarService pillarService;


    @Override
    public Page<ActionEntryResponse> getAll(Long userId, SearchRequestDto request) {

        Pageable pageable = PageableBuilderUtil.build(request);

        Specification<ActionEntry> dynamicSpec =
                SpecificationBuilderUtil.build(request);

        Specification<ActionEntry> mandatorySpec =
                UserSpecificationBuilderUtil.build(userId);

        Specification<ActionEntry> finalSpec =
                mandatorySpec.and(dynamicSpec);

        return actionEntryRepository.findAll(finalSpec, pageable)
                .map(actionEntryMapper::toDto);
    }

    @Override
    @Transactional
    public ActionEntryResponse create(ActionEntryCreateRequest request, Long userId) {

//      log.debug("Creating Action Entry for userId={} for date = {}", userId, request.date());

        LocalDate date = request.actionEntryDate();

        User user = userService.loadActiveUser(userId);

        DayEntry dayEntry = dayEntryService.loadOrCreateActiveDayEntry(user, date);

        String actionName = request.actionEntryName();
        ActionEntryStatus actionStatus = request.actionEntryStatus();
        ActivityNature nature = request.actionEntryNature();
        TrackingType trackingType = request.actionEntryTrackingType();

        Activity activity = null;
        Pillar pillar = null;

        if (request.activityId() != null) {
            activity = activityService.loadOwnedActiveActivity(request.activityId(), userId);
            actionName = activity.getActivityName();
            nature = activity.getActivityNature();
            trackingType = activity.getActivityTrackingType();
        } else if (request.pillarId() != null) {
            pillar = pillarService.loadOwnedActivePillar(request.pillarId(), userId);
        }

        ActionEntry actionEntry = ActionEntry.create(
                dayEntry,
                user,
                actionName,
                actionStatus,
                nature,
                trackingType
        );

        if (activity != null) {
            actionEntry.addActivity(activity);
        } else if (pillar != null) {
            actionEntry.addPillar(pillar);
        }

        actionEntry.setActionEntryNotes(request.actionEntryNotes());

        actionEntryRepository.save(actionEntry);

        return actionEntryMapper.toDto(actionEntry);
    }

    @Override
    public ActionEntryResponse update(ActionEntryUpdateRequest request, Long userId) {


        User user = userService.loadActiveUser(userId);
        ActionEntry actionEntry = loadOwnedActionEntry(request.actionEntryId(), userId);

        LocalDate date = request.actionEntryDate();
        DayEntry dayEntry = dayEntryService.loadOrCreateActiveDayEntry(user, date);

        String actionName = request.actionEntryName();
        ActionEntryStatus actionStatus = request.actionEntryStatus();
        ActivityNature nature = request.actionEntryNature();
        TrackingType trackingType = request.actionEntryTrackingType();

        Activity activity = null;
        Pillar pillar = null;

        if (request.activityId() != null) {
            activity = activityService.loadOwnedActiveActivity(request.activityId(), userId);
            actionName = activity.getActivityName();
            nature = activity.getActivityNature();
            trackingType = activity.getActivityTrackingType();
        } else if (request.pillarId() != null) {
            pillar = pillarService.loadOwnedActivePillar(request.pillarId(), userId);
        }

        actionEntry.create(
                dayEntry,
                user,
                actionName,
                actionStatus,
                nature,
                trackingType
        );

        actionEntry.setActionEntryNotes(request.actionEntryNotes());

        return actionEntryMapper.toDto(actionEntry);
    }

    @Override
    public ActionEntryResponse getById(Long id, Long userId) {
        ActionEntry actionEntry = loadOwnedActionEntry(id, userId);

        return actionEntryMapper.toDto(actionEntry);
    }


    @Override
    public ActionEntry loadOwnedActionEntry(Long actionEntryId, Long userId) {

        return actionEntryRepository
                .findByIdAndUserIdAndStatus(actionEntryId, userId, RecordStatus.ACTIVE)
                .orElseThrow(() ->
                        new NotFoundException("Pillar not found"));


    }


}

