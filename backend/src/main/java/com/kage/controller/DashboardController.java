package com.kage.controller;


import com.kage.dto.request.activity.ActivityDailyLogCreateRequest;
import com.kage.dto.request.activity.ActivityDailyLogUpdateRequest;
import com.kage.dto.response.ActivityDailyLogResponse;
import com.kage.dto.response.ApiResponse;
import com.kage.security.CustomUserDetails;
import com.kage.service.ActivityDailyLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final ActivityDailyLogService activityDailyLogService;

    @GetMapping("/toDo")
    public ResponseEntity<ApiResponse<List<ActivityDailyLogResponse>>> getToDo(
            @AuthenticationPrincipal CustomUserDetails user) {

        log.info("Fetching all active activity daily log");

        List<ActivityDailyLogResponse> data =
                activityDailyLogService.getToDo(user.getUser().getId(), LocalDate.now());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity daily log successfully",
                        data
                )
        );
    }

    @GetMapping("/completed")
    public ResponseEntity<ApiResponse<List<ActivityDailyLogResponse>>> getCompleted(
            @AuthenticationPrincipal CustomUserDetails user) {

        log.info("Fetching all active activity daily log");

        List<ActivityDailyLogResponse> data =
                activityDailyLogService.getCompleted(user.getUser().getId(), LocalDate.now());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity daily log successfully",
                        data
                )
        );
    }

    /**
     * Get activity daily log by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ActivityDailyLogResponse>> getById(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id) {

        log.info("Fetching activity daily log with id={}", id);

        ActivityDailyLogResponse data =
                activityDailyLogService.getById(id,user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity daily log successfully",
                        data
                )
        );
    }

    /**
     * Create a new activity daily log
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ActivityDailyLogResponse>> create(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActivityDailyLogCreateRequest request) {

//        log.info("Creating activity daily log with name={}", request.getName());

        ActivityDailyLogResponse data =
                activityDailyLogService.create(request , user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "activity daily log created successfully",
                        data
                ));
    }

    /**
     * Update an activity daily log
     */
    @PutMapping
    public ResponseEntity<ApiResponse<ActivityDailyLogResponse>> update(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActivityDailyLogUpdateRequest request) {

//        log.info("Updating activity daily log with id={}", id);

        ActivityDailyLogResponse data =
                activityDailyLogService.update( request, user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "activity daily log updated successfully",
                            data
                )
        );
    }

    /**
     * Deactivate (soft delete) an activity daily log
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id) {

        log.info("Deactivating activity daily log with id={}", id);

        activityDailyLogService.deactivate(id,user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "activity daily log deactivated successfully",
                        null
                )
        );
    }
}