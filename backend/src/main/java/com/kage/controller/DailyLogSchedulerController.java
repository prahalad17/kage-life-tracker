package com.kage.controller;


import com.kage.dto.request.activity.ActivityCreateRequest;
import com.kage.dto.request.activity.ActivityDailyLogCreateRequest;
import com.kage.dto.request.activity.ActivityDailyLogSchedulerRequest;
import com.kage.dto.request.activity.ActivityUpdateRequest;
import com.kage.dto.response.ActivityDailyLogResponse;
import com.kage.dto.response.ActivityDailyLogSchedulerResponse;
import com.kage.dto.response.ActivityResponse;
import com.kage.dto.response.ApiResponse;
import com.kage.security.CustomUserDetails;
import com.kage.service.ActivityDailyLogService;
import com.kage.service.ActivityService;
import com.kage.service.DailyLogSchedulerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scheduler")
@RequiredArgsConstructor
@Slf4j
public class DailyLogSchedulerController {

    private final DailyLogSchedulerService activityDailyLogService;



    /**
     * Create a new activity logs
     */
    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<ActivityDailyLogSchedulerResponse>> schedule(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActivityDailyLogSchedulerRequest request) {

        log.info("Creating activity with name={}", request.getDate());

        ActivityDailyLogSchedulerResponse data =
                activityDailyLogService.schedule(request.getDate() , user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "activity created successfully",
                        data
                ));
    }

}