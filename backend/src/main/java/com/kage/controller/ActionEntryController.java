package com.kage.controller;


import com.kage.common.dto.request.SearchRequestDto;
import com.kage.common.dto.response.PageResponse;
import com.kage.dto.request.action.ActionEntryCreateRequest;
import com.kage.dto.request.action.ActionEntryUpdateRequest;
import com.kage.dto.response.ActionEntryResponse;
import com.kage.dto.response.ApiResponse;
import com.kage.security.CustomUserDetails;
import com.kage.service.ActionEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/action-entry")
@RequiredArgsConstructor
@Slf4j
public class ActionEntryController {

    private final ActionEntryService actionEntryService;

    /**
     * Get all active day entries
     */
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<ActionEntryResponse>>> getAll(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody SearchRequestDto request) {

        log.info("Fetching all active day entries");

        Page<ActionEntryResponse> page =
                actionEntryService.getAll(user.getUser().getId(), request);

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
    public ResponseEntity<ApiResponse<ActionEntryResponse>> create(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActionEntryCreateRequest request) {

//        log.info("Creating day entry for ={}", request.date());

        ActionEntryResponse data =
                actionEntryService.create(request, user.getUser().getId());

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
    public ResponseEntity<ApiResponse<ActionEntryResponse>> getById(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id) {

        log.info("Fetching Day Entry For date ={}", id);

        ActionEntryResponse data =
                actionEntryService.getById(id, user.getUser().getId());

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
    public ResponseEntity<ApiResponse<ActionEntryResponse>> update(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid ActionEntryUpdateRequest request) {

        log.info("Updating action entry for ={}", request.dayEntryId());

        ActionEntryResponse data =
                actionEntryService.update(request, user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "activity created successfully",
                        data
                ));
    }
}