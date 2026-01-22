package com.kage.controller;


import com.kage.dto.request.ActivityTemplateCreateRequest;
import com.kage.dto.request.ActivityTemplateUpdateRequest;
import com.kage.dto.response.ActivityTemplateResponse;
import com.kage.dto.response.ApiResponse;
import com.kage.service.ActivityTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity-template")
@RequiredArgsConstructor
@Slf4j
public class ActivityTemplateController {

    private final ActivityTemplateService activityTemplateService;

    /**
     * Get all active activity templates
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ActivityTemplateResponse>>> getAll() {

        log.info("Fetching all active activity templates");

        List<ActivityTemplateResponse> data =
                activityTemplateService.getAll();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity template successfully",
                        data
                )
        );
    }

    /**
     * Get activity template by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ActivityTemplateResponse>> getById(
            @PathVariable Long id) {

        log.info("Fetching activity template with id={}", id);

        ActivityTemplateResponse data =
                activityTemplateService.getById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity template successfully",
                        data
                )
        );
    }

    /**
     * Create a new activity template
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ActivityTemplateResponse>> create(
            @RequestBody @Valid ActivityTemplateCreateRequest request) {

        log.info("Creating activity template with name={}", request.getName());

        ActivityTemplateResponse data =
                activityTemplateService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Activity Template created successfully",
                        data
                ));
    }

    /**
     * Update an activity template
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ActivityTemplateResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid ActivityTemplateUpdateRequest request) {

        log.info("Updating activity template with id={}", id);

        ActivityTemplateResponse data =
                activityTemplateService.update(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Activity Template updated successfully",
                            data
                )
        );
    }

    /**
     * Deactivate (soft delete) an activity template
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        log.info("Deactivating activity template with id={}", id);

        activityTemplateService.deactivate(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Activity template deactivated successfully",
                        null
                )
        );
    }
}