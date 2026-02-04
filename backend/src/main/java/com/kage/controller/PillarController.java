package com.kage.controller;


import com.kage.dto.request.pillar.PillarCreateRequest;
import com.kage.dto.request.pillar.PillarUpdateRequest;
import com.kage.dto.response.ApiResponse;
import com.kage.dto.response.PillarResponse;
import com.kage.security.CustomUserDetails;
import com.kage.service.PillarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pillar")
@RequiredArgsConstructor
@Slf4j
public class PillarController {

    private final PillarService pillarService;

    /**
     * Get all active user pillars
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PillarResponse>>> getAll(
            @AuthenticationPrincipal CustomUserDetails user) {

        log.info("Fetching all active user pillars");

        List<PillarResponse> data =
                pillarService.getAll(user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched user pillars successfully",
                        data
                )
        );
    }

    /**
     * Get user pillar by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PillarResponse>> getById(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id) {

        log.info("Fetching user pillar with id={}", id);

        PillarResponse data =
                pillarService.getById(id,user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched user pillar successfully",
                        data
                )
        );
    }

    /**
     * Create a new user pillar
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PillarResponse>> create(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid PillarCreateRequest request) {

        log.info("Creating user pillar with name={}", request);

        PillarResponse data =
                pillarService.create(request ,user.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "User pillar created successfully",
                        data
                ));
    }

    /**
     * Update a user pillar
     */
    @PutMapping
    public ResponseEntity<ApiResponse<PillarResponse>> update(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid PillarUpdateRequest request) {

        log.info("Updating user pillar with id={}", request.getId());

        PillarResponse data =
                pillarService.update(request, user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User pillar updated successfully",
                            data
                )
        );
    }

    /**
     * Deactivate (soft delete) a user pillar
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id) {

        log.info("Deactivating user pillar with id={}", id);

        pillarService.deactivate(id,user.getUser().getId());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User pillar deactivated successfully",
                        null
                )
        );
    }
}