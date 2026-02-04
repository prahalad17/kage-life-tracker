package com.kage.service.impl;

import com.kage.dto.request.pillar.PillarTemplateCreateRequest;
import com.kage.dto.request.pillar.PillarTemplateUpdateRequest;
import com.kage.dto.response.PillarTemplateResponse;
import com.kage.entity.PillarTemplate;
import com.kage.enums.RecordStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.PillarTemplateMapper;
import com.kage.repository.PillarTemplateRepository;
import com.kage.service.PillarTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PillarTemplateServiceImpl implements PillarTemplateService {

    private final PillarTemplateRepository pillarTemplateRepository;
    private final PillarTemplateMapper pillarTemplateMapper;

    /**
     * Create a new master pillar
     */
    @Override
    public PillarTemplateResponse create(PillarTemplateCreateRequest request) {


        if (pillarTemplateRepository.existsByNameIgnoreCaseAndStatus(request.getName(), RecordStatus.ACTIVE)) {
            throw new BusinessException("Pillar template with this name already exists");
        }

         PillarTemplate template =
                PillarTemplate.create(
                        request.getName(),
                        request.getDescription()
                );

        pillarTemplateRepository.save(template);

        log.info("Pillar template created with id={}", template.getId());

        return pillarTemplateMapper.toResponse(template);
    }

    /**
     * Get master pillar by id
     */
    @Override
    @Transactional(readOnly = true)
    public PillarTemplateResponse getById(Long id) {

        PillarTemplate template = loadActiveTemplate(id);
        return pillarTemplateMapper.toResponse(template);
    }

    /**
     * Get all active master pillars
     */
    @Override
    @Transactional(readOnly = true)
    public List<PillarTemplateResponse> getAll() {

        return pillarTemplateRepository.findByStatus(RecordStatus.ACTIVE)
                .stream()
                .map(pillarTemplateMapper::toResponse)
                .toList();
    }

    /**
     * Update master pillar
     */
    @Override
    public PillarTemplateResponse update(PillarTemplateUpdateRequest request) {

        PillarTemplate template = loadActiveTemplate(request.getId());

        if (!template.getName().equalsIgnoreCase(request.getName())
                && pillarTemplateRepository.existsByNameIgnoreCaseAndStatus(template.getName(), RecordStatus.ACTIVE)) {
            throw new BusinessException("Another pillar template already uses this name");
        }

        template.rename(request.getName());
        template.updateDescription(request.getDescription());

        pillarTemplateRepository.save(template);

        log.info("Pillar template updated with id={}", template.getId());

        return pillarTemplateMapper.toResponse(template);
    }

    /**
     * Soft delete (deactivate)
     */
    @Override
    public void deactivate(Long id) {

        PillarTemplate template = loadActiveTemplate(id);
        template.deactivate();

        log.info("Pillar template deactivated with id={}", id);
    }

    /* ---------- shared loader ---------- */

    @Transactional(readOnly = true)
    @Override
    public PillarTemplate loadActiveTemplate(Long templateId) {
        return pillarTemplateRepository
                .findByIdAndStatus(templateId, RecordStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Pillar template not found"));
    }
}
