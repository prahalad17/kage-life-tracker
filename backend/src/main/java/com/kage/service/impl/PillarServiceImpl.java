package com.kage.service.impl;

import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.pillar.PillarCreateRequest;
import com.kage.dto.request.pillar.PillarUpdateRequest;
import com.kage.dto.response.PillarResponse;
import com.kage.entity.Pillar;
import com.kage.entity.PillarTemplate;
import com.kage.entity.User;
import com.kage.enums.RecordStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.PillarMapper;
import com.kage.repository.PillarRepository;
import com.kage.service.PillarService;
import com.kage.util.PageableBuilderUtil;
import com.kage.util.SpecificationBuilderUtil;
import com.kage.util.UserSpecificationBuilderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PillarServiceImpl implements PillarService {

    private final PillarRepository pillarRepository;
    private final PillarMapper pillarMapper;
    private final UserServiceImpl userService;
    private final PillarTemplateServiceImpl pillarTemplateService;

    /**
     * Create a new user pillar
     */
    @Override
    public PillarResponse create(PillarCreateRequest request, Long userId) {

        log.debug("Creating pillar for userId={}", userId);

        User user = userService.loadActiveUser(userId);

        Pillar pillar = Pillar.create(
                user,
                request.pillarName()
        );

        if (request.pillarTemplateId() != null) {
            PillarTemplate template = pillarTemplateService.loadActiveTemplate(request.pillarTemplateId());
            pillar.assignTemplate(template);
        }

        pillar.setColor(request.pillarColor());
        pillar.setOrderIndex(request.orderIndex());
        pillar.setPriorityWeight(request.priorityWeight());
        pillar.updateDescription(request.pillarDescription());

        Pillar saved = pillarRepository.save(pillar);

        log.info("Pillar created with id={}", saved.getId());

        return pillarMapper.toDto(saved);
    }

    /**
     * Get user pillar by id
     */
    @Override
    @Transactional(readOnly = true)
    public PillarResponse getById(Long id, Long userId) {

        log.debug("Fetching user pillar with id={}", id);

        Pillar pillar = loadOwnedActivePillar(id, userId);

        return pillarMapper.toDto(pillar);
    }

    /**
     * Get all active user pillars
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PillarResponse> getAll(Long userId, SearchRequestDto request) {

        Pageable pageable = PageableBuilderUtil.build(request);

        Specification<Pillar> dynamicSpec =
                SpecificationBuilderUtil.build(request);

        Specification<Pillar> mandatorySpec =
                UserSpecificationBuilderUtil.build(userId);

        Specification<Pillar> finalSpec =
                mandatorySpec.and(dynamicSpec);

        return pillarRepository.findAll(finalSpec, pageable)
                .map(pillarMapper::toDto);
    }

    /**
     * Update user pillar
     */
    @Override
    public PillarResponse update(PillarUpdateRequest request, Long userId) {

//        log.debug("Updating user pillar with id={}", id);

        Pillar pillar = loadOwnedActivePillar(request.pillarId(), userId);

        if (request.pillarTemplateId() != null) {
            PillarTemplate template = pillarTemplateService.loadActiveTemplate(request.pillarTemplateId());
            pillar.assignTemplate(template);
        }

        pillar.setColor(request.pillarColor());
        pillar.setOrderIndex(request.orderIndex());
        pillar.setPriorityWeight(request.priorityWeight());
        pillar.updateDescription(request.pillarDescription());

        Pillar updated = pillarRepository.save(pillar);

        log.info("Pillar updated with id={}", updated.getId());

        return pillarMapper.toDto(updated);
    }

    /**
     * Soft delete (deactivate)
     */
    @Override
    public void deactivate(Long id, Long userId) {

        log.debug("Deactivating user pillar with id={}", id);

        Pillar pillar = loadOwnedActivePillar(id, userId);

        pillar.deactivate();

        pillarRepository.save(pillar);

        log.info("User pillar deactivated successfully with id={}", id);
    }


    @Transactional(readOnly = true)
    @Override
    public Pillar loadActivePillar(Long id) {
        return pillarRepository
                .findByIdAndStatus(id, RecordStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Pillar not found"));
    }

    /* -----------------------------------------------------
       Internal helpers (aggregate loaders)
       ----------------------------------------------------- */

    @Override
    public Pillar loadOwnedActivePillar(Long id, Long userId) {

        Pillar pillar = pillarRepository
                .findByIdAndUserIdAndStatus(id, userId, RecordStatus.ACTIVE)
                .orElseThrow(() ->
                        new NotFoundException("Pillar not found"));

        if (!pillar.getUser().getId().equals(userId)) {
            throw new BusinessException("User does not own this pillar");
        }

        return pillar;
    }


}
