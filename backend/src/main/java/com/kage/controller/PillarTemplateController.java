package com.kage.controller;


import com.kage.dto.request.PillarTemplateCreateRequest;
import com.kage.dto.request.PillarTemplateUpdateRequest;
import com.kage.dto.response.ApiResponse;
import com.kage.dto.response.PillarTemplateResponse;
import com.kage.service.PillarTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/master-pillars")
@RequiredArgsConstructor
@Slf4j
public class PillarTemplateController {

    private final PillarTemplateService pillarTemplateService;

    /**
     * Get all active master pillars
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PillarTemplateResponse>>> getAll() {

        log.info("Fetching all active master pillars");

        List<PillarTemplateResponse> data =
                pillarTemplateService.getAll();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched master pillars successfully",
                        data
                )
        );
    }

    /**
     * Get master pillar by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PillarTemplateResponse>> getById(
            @PathVariable Long id) {

        log.info("Fetching master pillar with id={}", id);

        PillarTemplateResponse data =
                pillarTemplateService.getById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched master pillar successfully",
                        data
                )
        );
    }

    /**
     * Create a new master pillar
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PillarTemplateResponse>> create(
            @RequestBody @Valid PillarTemplateCreateRequest request) {

        log.info("Creating master pillar with name={}", request.getName());

        PillarTemplateResponse data =
                pillarTemplateService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Master pillar created successfully",
                        data
                ));
    }

    /**
     * Update a master pillar
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PillarTemplateResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid PillarTemplateUpdateRequest request) {

        log.info("Updating master pillar with id={}", id);

        PillarTemplateResponse data =
                pillarTemplateService.update(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Master pillar updated successfully",
                            data
                )
        );
    }

    /**
     * Deactivate (soft delete) a master pillar
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        log.info("Deactivating master pillar with id={}", id);

        pillarTemplateService.deactivate(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Master pillar deactivated successfully",
                        null
                )
        );
    }
}