package com.kage.scheduler;


import com.kage.dto.request.activity.ActivityDailyLogSchedulerRequest;
import com.kage.dto.response.ActivityDailyLogResponse;
import com.kage.dto.response.ApiResponse;
import com.kage.security.CustomUserDetails;
import com.kage.service.ActivityDailyLogService;
import com.kage.service.DailyLogSchedulerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@Component
public class DailyLogScheduler {

    private final DailyLogSchedulerService activityDailyLogService;



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