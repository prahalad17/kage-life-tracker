package com.kage.controller;


import com.kage.dto.request.activity.ActionPlanSchedulerRequest;
import com.kage.dto.response.ActionPlanSchedulerResponse;
import com.kage.dto.response.ApiResponse;
import com.kage.security.CustomUserDetails;
import com.kage.service.ActionPlanSchedulerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/scheduler")
@RequiredArgsConstructor
@Slf4j
public class DailyLogSchedulerController {

    private final ActionPlanSchedulerService activityDailyLogService;



    /**
     * Create a new activity logs
     */
    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<ActionPlanSchedulerResponse>> schedule(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActionPlanSchedulerRequest request) {

        log.info("Creating activity with name={}", request.getDate());

        ActionPlanSchedulerResponse data =
                activityDailyLogService.schedule(request.getDate() , user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "activity created successfully",
                        data
                ));
    }

}