package com.kage.service.impl;

import com.kage.dto.request.ActivityTemplateCreateRequest;
import com.kage.dto.request.ActivityTemplateUpdateRequest;
import com.kage.dto.response.ActivityTemplateResponse;
import com.kage.entity.ActivityTemplate;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.ActivityTemplateMapper;
import com.kage.repository.ActivityTemplateRepository;
import com.kage.service.ActivityTemplateService;
import com.kage.util.SanitizerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActivityTemplateServiceImpl implements ActivityTemplateService {

    private final ActivityTemplateRepository activityTemplateRepository;
    private final ActivityTemplateMapper activityTemplateMapper;

    /**
     * Create a new activity template
     */
    @Override
    public ActivityTemplateResponse create(ActivityTemplateCreateRequest request) {

        // 1️⃣ Sanitize input
        String cleanName = SanitizerUtil.clean(request.getName());
        String cleanDescription = SanitizerUtil.clean(request.getDescription());

        log.debug("Sanitized activity template name={}", cleanName);

        // 2️⃣ Business validation
        if (activityTemplateRepository.existsByNameIgnoreCase(cleanName)) {
            log.warn("Activity Template already exists with name={}", cleanName);
            throw new BusinessException("Activity Template with this name already exists");
        }

        // 3️⃣ Map DTO → Entity
        ActivityTemplate activity = activityTemplateMapper.toEntity(request);
        activity.setName(cleanName);
        activity.setDescription(cleanDescription);
//        pillar.setActive(true);

        // 4️⃣ Persist
        ActivityTemplate saved = activityTemplateRepository.save(activity);

        log.info("Activity Template created successfully with id={}", saved.getId());

        // 5️⃣ Map Entity → DTO
        return activityTemplateMapper.toDto(saved);
    }

    /**
     * Get Activity Template by id
     */
    @Override
    @Transactional(readOnly = true)
    public ActivityTemplateResponse getById(Long id) {

        log.debug("Fetching Activity Template with id={}", id);

        ActivityTemplate activity = activityTemplateRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    log.warn("Activity Template not found with id={}", id);
                    return new NotFoundException("Activity Template not found");
                });

        return activityTemplateMapper.toDto(activity);
    }

    /**
     * Get all active Activity Templates
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActivityTemplateResponse> getAll() {

        log.debug("Fetching all active Activity Templates");

        return activityTemplateRepository.findByActiveTrue()
                .stream()
                .map(activityTemplateMapper::toDto)
                .toList();
    }

    /**
     * Update Activity Template
     */
    @Override
    public ActivityTemplateResponse update(Long id, ActivityTemplateUpdateRequest request) {

        log.debug("Updating Activity Template with id={}", id);

        ActivityTemplate activity = activityTemplateRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update. Activity Template not found with id={}", id);
                    return new NotFoundException("Activity Template not found");
                });

        // 1️⃣ Sanitize inputs
        String cleanName = SanitizerUtil.clean(request.getName());
        String cleanDescription = SanitizerUtil.clean(request.getDescription());

        // 2️⃣ Business rule: unique name (only if changed)
        if (!activity.getName().equalsIgnoreCase(cleanName)
                && activityTemplateRepository.existsByNameIgnoreCase(cleanName)) {

            log.warn("Duplicate Activity Template name during update: {}", cleanName);
            throw new BusinessException("Another Activity Template already uses this name");
        }

        // 3️⃣ Map updates (MapStruct)
        activityTemplateMapper.partialUpdate(request, activity);
        activity.setName(cleanName);
        activity.setDescription(cleanDescription);

        // 4️⃣ Save
        ActivityTemplate updated = activityTemplateRepository.save(activity);

        log.info("Activity Template updated successfully with id={}", updated.getId());

        return activityTemplateMapper.toDto(updated);
    }

    /**
     * Soft delete (deactivate)
     */
    @Override
    public void deactivate(Long id) {

        log.debug("Deactivating Activity Template with id={}", id);

        ActivityTemplate activity = activityTemplateRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    log.warn("Cannot deactivate. Activity Template not found with id={}", id);
                    return new NotFoundException("Activity Template not found");
                });

        activity.setActive(false);
        activityTemplateRepository.save(activity);

        log.info("Activity Template deactivated successfully with id={}", id);
    }
}
