package com.kage.scheduler;


import com.kage.service.ActionPlanSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@Component
public class DailyLogScheduler {

    private final ActionPlanSchedulerService activityDailyLogService;



    /**
     * Creates baseline activity logs for today
     */
    @Scheduled(
            cron = "0 0 0 * * ?",
            zone = "Asia/Kolkata"
    )
    public void schedule() {

        LocalDate today = LocalDate.now();

        log.info("DailyLogScheduler triggered for date={}", today);

        try {
            activityDailyLogService.schedule(today, null);
            log.info("Daily activity logs ensured successfully for date={}", today);

        } catch (Exception ex) {
            log.error(
                    "Failed to generate daily activity logs for date={}",
                    today,
                    ex
            );
        }
    }

}