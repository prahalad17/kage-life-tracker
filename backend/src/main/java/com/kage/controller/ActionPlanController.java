package com.kage.controller;


import com.kage.common.dto.request.SearchRequestDto;
import com.kage.common.dto.response.PageResponse;
import com.kage.dto.request.action.ActionPlanCompleteRequest;
import com.kage.dto.request.action.ActionPlanCreateRequest;
import com.kage.dto.request.action.ActionPlanUpdateRequest;
import com.kage.dto.response.ActionPlanResponse;
import com.kage.dto.response.ApiResponse;
import com.kage.security.CustomUserDetails;
import com.kage.service.ActionPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/action-plan")
@RequiredArgsConstructor
@Slf4j
public class ActionPlanController {

    private final ActionPlanService actionPlanService;

    /**
     * Get all active day entries
     */
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<ActionPlanResponse>>> getAll(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody SearchRequestDto request) {

        log.info("Fetching all active day entries");

        Page<ActionPlanResponse> page =
                actionPlanService.getAll(user.getUser().getId(), request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity successfully",
                        new PageResponse<>(page)
                )
        );
    }

    /**
     * Create a new action entry
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ActionPlanResponse>> create(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActionPlanCreateRequest request) {

//        log.info("Creating day entry for ={}", request.date());

        ActionPlanResponse data =
                actionPlanService.create(request, user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "activity created successfully",
                        data
                ));
    }

    /**
     * Get Day Entry by date
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ActionPlanResponse>> getById(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id) {

        log.info("Fetching Day Entry For date ={}", id);

        ActionPlanResponse data =
                actionPlanService.getById(id, user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity successfully",
                        data
                )
        );
    }


    /**
     * Update a day entry
     */
    @PutMapping
    public ResponseEntity<ApiResponse<ActionPlanResponse>> update(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActionPlanUpdateRequest request) {

        log.info("Updating action entry for ={}", request.actionPlanId());

        ActionPlanResponse data =
                actionPlanService.update(request, user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "activity created successfully",
                        data
                ));
    }

    /**
     * Complete An Action Plan
     */
    @PutMapping("/complete")
    public ResponseEntity<ApiResponse<ActionPlanResponse>> complete(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActionPlanCompleteRequest
                    request) {

        log.info("Updating action entry for ={}", request.actionPlanId());

        ActionPlanResponse data =
                actionPlanService.completeActionPlan(request, user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "activity created successfully",
                        data
                ));
    }
}