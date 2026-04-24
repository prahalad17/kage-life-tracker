package com.kage.service.impl;

import com.kage.dto.response.ActionPlanSchedulerResponse;
import com.kage.entity.ActionPlan;
import com.kage.entity.Activity;
import com.kage.entity.User;
import com.kage.enums.PlanSource;
import com.kage.enums.RecordStatus;
import com.kage.enums.ScheduleType;
import com.kage.mapper.ActivityMapper;
import com.kage.repository.ActionPlanRepository;
import com.kage.repository.ActivityRepository;
import com.kage.service.ActionPlanSchedulerService;
import com.kage.service.PillarService;
import com.kage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActionPlanSchedulerServiceImpl implements ActionPlanSchedulerService {

    private final ActivityRepository activityRepository;
    private final ActionPlanRepository actionPlanRepository;
    private final UserService userService;
    private final PillarService pillarService;
    private final ActivityMapper activityMapper;


    @Override
    public ActionPlanSchedulerResponse schedule(LocalDate date, Long userId) {

        List<Activity> activities =
                activityRepository.findAllByStatus(RecordStatus.ACTIVE);

        User user = userService.loadActiveUser(userId);


        int created = 0;
        int skipped = 0;

        for (Activity activity : activities) {

            if (!appliesToDate(activity, date)) {
                continue;
            }

            boolean exists =
                    actionPlanRepository
                            .existsByActivityIdAndUserAndActionPlanDateAndActionPlanSourceAndStatus(
                                    activity.getId(),
                                    user,
                                    date,
                                    PlanSource.SYSTEM_BASELINE,
                                    RecordStatus.ACTIVE
                            );

            if (exists) {
                skipped++;
                continue;
            }

            ActionPlan actionPlan = ActionPlan.createSystemActionPlan(
                    user,
                    activity,
                    date
            );

            actionPlanRepository.save(actionPlan);
            created++;
        }

        return new ActionPlanSchedulerResponse(created,skipped);
    }

    private boolean appliesToDate(Activity activity, LocalDate date) {

        if (!activity.isActive()) {
            return false;
        }

        return switch (activity.getSchedule().getType()) {

            case ScheduleType.DAILY -> true;

            default -> false;
        };
    }

}

