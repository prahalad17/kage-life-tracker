package com.kage.service.impl;

import com.kage.dto.response.ActivityDailyLogSchedulerResponse;
import com.kage.entity.Activity;
import com.kage.entity.ActivityDailyLog;
import com.kage.enums.LogSource;
import com.kage.enums.RecordStatus;
import com.kage.enums.ScheduleType;
import com.kage.mapper.ActivityMapper;
import com.kage.repository.ActivityDailyLogRepository;
import com.kage.repository.ActivityRepository;
import com.kage.service.DailyLogSchedulerService;
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
public class DailyLogSchedulerServiceImpl implements DailyLogSchedulerService {

    private final ActivityRepository activityRepository;
    private final ActivityDailyLogRepository activityDailyLogRepository;
    private final UserService userService;
    private final PillarService pillarService;
    private final ActivityMapper activityMapper;


    @Override
    public ActivityDailyLogSchedulerResponse schedule(LocalDate request, Long userId) {

        List<Activity> activities =
                activityRepository.findAllByStatus(RecordStatus.ACTIVE);

        LocalDate date = LocalDate.now();

        int created = 0;
        int skipped = 0;

        for (Activity activity : activities) {

            if (!appliesToDate(activity, date)) {
                continue;
            }

            boolean exists =
                    activityDailyLogRepository
                            .existsByActivityIdAndUserIdAndLogDateAndLogSource(
                                    activity.getId(),
                                    activity.getUser().getId(),
                                    date,
                                    LogSource.SYSTEM_BASELINE
                            );

            if (exists) {
                skipped++;
                continue;
            }

            ActivityDailyLog baseline =
                    ActivityDailyLog.createBaseline(
                            activity,
                            activity.getUser(),
                            date
                    );

            activityDailyLogRepository.save(baseline);
            created++;
        }

        return new ActivityDailyLogSchedulerResponse("");
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

