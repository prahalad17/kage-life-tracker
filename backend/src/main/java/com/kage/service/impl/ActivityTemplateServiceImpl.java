package com.kage.service.impl;

import com.kage.dto.request.activity.ActivityTemplateCreateRequest;
import com.kage.dto.request.activity.ActivityTemplateUpdateRequest;
import com.kage.dto.response.ActivityTemplateResponse;
import com.kage.entity.ActivityTemplate;
import com.kage.entity.PillarTemplate;
import com.kage.enums.RecordStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.ActivityTemplateMapper;
import com.kage.repository.ActivityTemplateRepository;
import com.kage.repository.PillarTemplateRepository;
import com.kage.service.ActivityTemplateService;
import com.kage.service.PillarTemplateService;
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


    private final ActivityTemplateRepository repository;
    private final ActivityTemplateMapper mapper;
    private final PillarTemplateService pillarTemplateService;

    /**
     * Create a new activity template
     */
    @Override
    public ActivityTemplateResponse create(ActivityTemplateCreateRequest request) {
        PillarTemplate pillarTemplate =
                pillarTemplateService.loadActiveTemplate(
                        request.getPillarTemplateId()
                );

        if (repository.existsByPillarTemplateAndNameIgnoreCaseAndStatus(
                pillarTemplate,
                request.getName(),
                RecordStatus.ACTIVE)) {
            throw new BusinessException(
                    "Activity template with this name already exists for this pillar"
            );
        }

        ActivityTemplate template = ActivityTemplate.create(
                pillarTemplate,
                request.getName(),
                request.getNature(),
                request.getDefaultTrackingType(),
                request.getDefaultUnit(),
                request.getDescription()
        );

        repository.save(template);

        log.info("ActivityTemplate created with id={}", template.getId());
        return mapper.toDto(template);
    }

    @Override
    @Transactional(readOnly = true)
    public ActivityTemplateResponse getById(Long id) {

        ActivityTemplate template = loadActiveTemplate(id);
        return mapper.toDto(template);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityTemplateResponse> getAll() {

        return repository.findByStatus(RecordStatus.ACTIVE)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ActivityTemplateResponse update(ActivityTemplateUpdateRequest request) {

        ActivityTemplate template =
                loadActiveTemplate(request.getActivityId());

        PillarTemplate pillarTemplate =
                pillarTemplateService.loadActiveTemplate(
                        request.getPillarTemplateId()
                );

        if (!template.getName().equalsIgnoreCase(request.getName())
                && repository.existsByPillarTemplateAndNameIgnoreCaseAndStatus(
                pillarTemplate,
                request.getName(),
                RecordStatus.ACTIVE)) {
            throw new BusinessException(
                    "Another activity template already uses this name for this pillar"
            );
        }

        template.rename(request.getName());
        template.updateDescription(request.getDescription());
        template.changeTemplate(pillarTemplate);

        return mapper.toDto(template);
    }

    @Override
    public void deactivate(Long id) {

        ActivityTemplate template = loadActiveTemplate(id);
        template.deactivate();

        log.info("ActivityTemplate deactivated with id={}", id);
    }

    /* ---------- shared loader ---------- */

    @Transactional(readOnly = true)
    public ActivityTemplate loadActiveTemplate(Long id) {
        return repository
                .findByIdAndStatus(id, RecordStatus.ACTIVE)
                .orElseThrow(() ->
                        new NotFoundException("Activity template not found"));
    }
}
