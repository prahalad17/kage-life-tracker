package com.kage.service.impl;

import com.kage.dto.request.pillar.PillarCreateRequest;
import com.kage.dto.request.pillar.PillarUpdateRequest;
import com.kage.dto.response.PillarResponse;
import com.kage.entity.Activity;
import com.kage.entity.Pillar;
import com.kage.entity.PillarTemplate;
import com.kage.entity.User;
import com.kage.enums.RecordStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.PillarMapper;
import com.kage.repository.PillarRepository;
import com.kage.service.PillarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

        PillarTemplate template = pillarTemplateService.loadActiveTemplate(request.getPillarTemplateId());

        Pillar pillar = Pillar.create(
                user,
                template.getName()
        );

        pillar.assignTemplate(template);
        pillar.updateDescription(template.getDescription());
        Pillar saved = pillarRepository.save(pillar);


        log.info("Pillar created with id={}", saved.getId());

        return pillarMapper.toResponse(saved);
    }

    /**
     * Get user pillar by id
     */
    @Override
    @Transactional(readOnly = true)
    public PillarResponse getById(Long id,Long userId) {

        log.debug("Fetching user pillar with id={}", id);

        Pillar pillar = loadOwnedActivePillar(id,userId);

        return pillarMapper.toResponse(pillar);
    }

    /**
     * Get all active user pillars
     */
    @Override
    @Transactional(readOnly = true)
    public List<PillarResponse> getAll(Long userId) {

        log.debug("Fetching all active user pillars");

        return pillarRepository.findByUserIdAndStatus(userId,RecordStatus.ACTIVE)
                .stream()
                .map(pillarMapper::toResponse)
                .toList();
    }

    /**
     * Update user pillar
     */
    @Override
    public PillarResponse update( PillarUpdateRequest request, Long userId) {

//        log.debug("Updating user pillar with id={}", id);

        Pillar pillar = loadOwnedActivePillar(request.getId(),userId);

        PillarTemplate template = pillarTemplateService.loadActiveTemplate(request.getPillarTemplateId());


        pillar.rename(template.getName());
        pillar.updateDescription(template.getDescription());
        pillar.assignTemplate(template);

        Pillar updated = pillarRepository.save(pillar);

        log.info("Pillar updated with id={}", updated.getId());

        return pillarMapper.toResponse(updated);
    }

    /**
     * Soft delete (deactivate)
     */
    @Override
    public void deactivate(Long id, Long userId) {

        log.debug("Deactivating user pillar with id={}", id);

        Pillar pillar = loadOwnedActivePillar(id,userId);

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

    @Transactional(readOnly = true)
    protected Pillar loadOwnedActivePillar(Long id, Long userId) {

        Pillar pillar = pillarRepository
                .findByIdAndUserIdAndStatus(id,userId, RecordStatus.ACTIVE)
                .orElseThrow(() ->
                        new NotFoundException("Pillar not found"));

        if (!pillar.getUser().getId().equals(userId)) {
            throw new BusinessException("User does not own this pillar");
        }

        return pillar;
    }


}
