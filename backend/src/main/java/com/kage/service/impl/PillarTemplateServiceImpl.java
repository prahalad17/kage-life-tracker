package com.kage.service.impl;

import com.kage.dto.request.PillarTemplateCreateRequest;
import com.kage.dto.request.PillarTemplateUpdateRequest;
import com.kage.dto.response.PillarTemplateResponse;
import com.kage.entity.PillarTemplate;
import com.kage.enums.RecordStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.PillarTemplateMapper;
import com.kage.repository.PillarTemplateRepository;
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
public class PillarTemplateServiceImpl implements PillarTemplateService {

    private final PillarTemplateRepository pillarTemplateRepository;
    private final PillarTemplateMapper pillarTemplateMapper;

    /**
     * Create a new master pillar
     */
    @Override
    public PillarTemplateResponse create(PillarTemplateCreateRequest request) {

        // 1️⃣ Sanitize input
        String cleanName = SanitizerUtil.clean(request.getName());
        String cleanDescription = SanitizerUtil.clean(request.getDescription());

        log.debug("Sanitized master pillar name={}", cleanName);

        // 2️⃣ Business validation
        if (pillarTemplateRepository.existsByNameIgnoreCase(cleanName)) {
            log.warn("Master pillar already exists with name={}", cleanName);
            throw new BusinessException("Master pillar with this name already exists");
        }

        // 3️⃣ Map DTO → Entity
        PillarTemplate pillar = pillarTemplateMapper.toEntity(request);
        pillar.setName(cleanName);
        pillar.setMasterPillarId(1L);
        pillar.setDescription(cleanDescription);
        pillar.setActive(true);

        // 4️⃣ Persist
        PillarTemplate saved = pillarTemplateRepository.save(pillar);

        log.info("Master pillar created successfully with id={}", saved.getId());

        // 5️⃣ Map Entity → DTO
        return pillarTemplateMapper.toResponse(saved);
    }

    /**
     * Get master pillar by id
     */
    @Override
    @Transactional(readOnly = true)
    public PillarTemplateResponse getById(Long id) {

        log.debug("Fetching master pillar with id={}", id);

        PillarTemplate pillar = pillarTemplateRepository
                .findByIdAndStatus(id, RecordStatus.ACTIVE)
                .orElseThrow(() -> {
                    log.warn("Master pillar not found with id={}", id);
                    return new NotFoundException("Master pillar not found");
                });

        return pillarTemplateMapper.toResponse(pillar);
    }

    /**
     * Get all active master pillars
     */
    @Override
    @Transactional(readOnly = true)
    public List<PillarTemplateResponse> getAll() {

        log.debug("Fetching all active master pillars");

        return pillarTemplateRepository.findByActiveTrue()
                .stream()
                .map(pillarTemplateMapper::toResponse)
                .toList();
    }

    /**
     * Update master pillar
     */
    @Override
    public PillarTemplateResponse update(Long id, PillarTemplateUpdateRequest request) {

        log.debug("Updating master pillar with id={}", id);

        PillarTemplate pillar = pillarTemplateRepository
                .findByIdAndStatus(id, RecordStatus.ACTIVE)
                .orElseThrow(() -> {
                    log.warn("Cannot update. Master pillar not found with id={}", id);
                    return new NotFoundException("Master pillar not found");
                });

        // 1️⃣ Sanitize inputs
        String cleanName = SanitizerUtil.clean(request.getName());
        String cleanDescription = SanitizerUtil.clean(request.getDescription());

        // 2️⃣ Business rule: unique name (only if changed)
        if (!pillar.getName().equalsIgnoreCase(cleanName)
                && pillarTemplateRepository.existsByNameIgnoreCase(cleanName)) {

            log.warn("Duplicate master pillar name during update: {}", cleanName);
            throw new BusinessException("Another master pillar already uses this name");
        }

        // 3️⃣ Map updates (MapStruct)
        pillarTemplateMapper.updateEntityFromDto(request, pillar);
        pillar.setName(cleanName);
        pillar.setDescription(cleanDescription);

        // 4️⃣ Save
        PillarTemplate updated = pillarTemplateRepository.save(pillar);

        log.info("Master pillar updated successfully with id={}", updated.getId());

        return pillarTemplateMapper.toResponse(updated);
    }

    /**
     * Soft delete (deactivate)
     */
    @Override
    public void deactivate(Long id) {

        log.debug("Deactivating master pillar with id={}", id);

        PillarTemplate pillar = pillarTemplateRepository
                .findByIdAndStatus(id, RecordStatus.ACTIVE)
                .orElseThrow(() -> {
                    log.warn("Cannot deactivate. Master pillar not found with id={}", id);
                    return new NotFoundException("Master pillar not found");
                });

        pillar.setActive(false);
        pillarTemplateRepository.save(pillar);

        log.info("Master pillar deactivated successfully with id={}", id);
    }
}
