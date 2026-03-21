package com.kage.service.impl;

import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.action.ActionPlanCreateRequest;
import com.kage.dto.request.action.ActionPlanUpdateRequest;
import com.kage.dto.response.ActionPlanResponse;
import com.kage.entity.*;
import com.kage.enums.RecordStatus;
import com.kage.exception.NotFoundException;
import com.kage.mapper.ActionPlanMapper;
import com.kage.repository.ActionPlanRepository;
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


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActionPlanServiceImpl implements ActionPlanService {

    private final ActionPlanRepository actionPlanRepository;
    private final ActionPlanMapper actionPlanMapper;
    private final UserService userService;
    private final DayEntryService dayEntryService;
    private final ActivityService activityService;
    private final PillarService pillarService;


    @Override
    public Page<ActionPlanResponse> getAll(Long userId, SearchRequestDto request) {

        Pageable pageable = PageableBuilderUtil.build(request);

        Specification<ActionPlan> dynamicSpec =
                SpecificationBuilderUtil.build(request);

        Specification<ActionPlan> mandatorySpec =
                UserSpecificationBuilderUtil.build(userId);

        Specification<ActionPlan> finalSpec =
                mandatorySpec.and(dynamicSpec);

        return actionPlanRepository.findAll(finalSpec, pageable)
                .map(actionPlanMapper::toDto);
    }

    @Override
    @Transactional
    public ActionPlanResponse create(ActionPlanCreateRequest request, Long userId) {

//        log.debug("Creating Action Entry for userId={} for date = {}", userId, request.date());
        User user = userService.loadActiveUser(userId);
        DayEntry dayEntry = dayEntryService.loadActiveDayEntry(userId, request.dayEntryId());

        ActionPlan actionPlan = ActionPlan.create(
                user,
                request.actionName(),
                request.actionStatus(),
                request.nature(),
                request.trackingType());

        if (request.activityId() != null) {

            Activity activity = activityService.loadOwnedActiveActivity(request.activityId(), userId);
            actionPlan.addActivity(activity);

        } else if (request.pillarId() != null) {

            Pillar pillar = pillarService.loadOwnedActivePillar(request.pillarId(), userId);
            actionPlan.addPillar(pillar);
        }

        actionPlanRepository.save(actionPlan);

        return actionPlanMapper.toDto(actionPlan);
    }

    @Override
    public ActionPlanResponse update(ActionPlanUpdateRequest request, Long userId) {

        ActionPlan actionPlan = loadOwnedActionPlan(request.actionPlanId(), userId);

        return actionPlanMapper.toDto(actionPlan);
    }

    @Override
    public ActionPlanResponse getById(Long id, Long userId) {
        ActionPlan actionPlan = loadOwnedActionPlan(id, userId);

        return actionPlanMapper.toDto(actionPlan);
    }


    @Override
    public ActionPlan loadOwnedActionPlan(Long actionPlanId, Long userId) {

        return actionPlanRepository
                .findByIdAndUserIdAndStatus(actionPlanId, userId, RecordStatus.ACTIVE)
                .orElseThrow(() ->
                        new NotFoundException("Pillar not found"));


    }


}

