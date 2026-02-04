package com.kage.controller;


import com.kage.dto.request.activity.ActivityCreateRequest;
import com.kage.dto.request.activity.ActivityUpdateRequest;
import com.kage.dto.response.ActivityResponse;
import com.kage.dto.response.ApiResponse;
import com.kage.security.CustomUserDetails;
import com.kage.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
@Slf4j
public class ActivityController {

    private final ActivityService activityService;

    /**
     * Get all active activity
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ActivityResponse>>> getAll(
            @AuthenticationPrincipal CustomUserDetails user) {

        log.info("Fetching all active activity");

        List<ActivityResponse> data =
                activityService.getAll(user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity successfully",
                        data
                )
        );
    }

    /**
     * Get activity by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ActivityResponse>> getById(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id) {

        log.info("Fetching activity with id={}", id);

        ActivityResponse data =
                activityService.getById(id,user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity successfully",
                        data
                )
        );
    }

    /**
     * Create a new activity
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ActivityResponse>> create(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActivityCreateRequest request) {

        log.info("Creating activity with name={}", request.getName());

        ActivityResponse data =
                activityService.create(request , user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "activity created successfully",
                        data
                ));
    }

    /**
     * Update an activity
     */
    @PutMapping
    public ResponseEntity<ApiResponse<ActivityResponse>> update(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActivityUpdateRequest request) {

//        log.info("Updating activity with id={}", id);

        ActivityResponse data =
                activityService.update( request, user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "activity updated successfully",
                            data
                )
        );
    }

    /**
     * Deactivate (soft delete) an activity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id) {

        log.info("Deactivating activity with id={}", id);

        activityService.deactivate(id,user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "activity deactivated successfully",
                        null
                )
        );
    }
}