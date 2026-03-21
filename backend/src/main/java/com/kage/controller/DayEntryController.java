package com.kage.controller;


import com.kage.common.dto.request.SearchRequestDto;
import com.kage.common.dto.response.PageResponse;
import com.kage.dto.request.day.DayEntryCreateRequest;
import com.kage.dto.request.day.DayEntryUpdateRequest;
import com.kage.dto.response.ApiResponse;
import com.kage.dto.response.DayEntryResponse;
import com.kage.security.CustomUserDetails;
import com.kage.service.DayEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/day-entry")
@RequiredArgsConstructor
@Slf4j
public class DayEntryController {

    private final DayEntryService dayEntryService;

    /**
     * Get all active day entries
     */
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<DayEntryResponse>>> getAll(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody SearchRequestDto request) {

        log.info("Fetching all active day entries");

        Page<DayEntryResponse> page =
                dayEntryService.getAll(user.getUser().getId(), request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity successfully",
                        new PageResponse<>(page)
                )
        );
    }

    /**
     * Get Day Entry by date
     */
    @GetMapping("/{date}")
    public ResponseEntity<ApiResponse<DayEntryResponse>> getById(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable LocalDate date) {

        log.info("Fetching Day Entry For date ={}", date);

        DayEntryResponse data =
                dayEntryService.getByDate(date, user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched activity successfully",
                        data
                )
        );
    }

    /**
     * Create a new day entry
     */
    @PostMapping
    public ResponseEntity<ApiResponse<DayEntryResponse>> create(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid DayEntryCreateRequest request) {

        log.info("Creating day entry for ={}", request.date());

        DayEntryResponse data =
                dayEntryService.create(request, user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "activity created successfully",
                        data
                ));
    }

    /**
     * Update a day entry
     */
    @PutMapping
    public ResponseEntity<ApiResponse<DayEntryResponse>> update(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid DayEntryUpdateRequest request) {

        log.info("Updating day entry for ={}", request.dayEntryId());

        DayEntryResponse data =
                dayEntryService.update(request, user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "activity created successfully",
                        data
                ));
    }


}