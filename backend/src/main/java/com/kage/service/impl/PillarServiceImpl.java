package com.kage.service.impl;

import com.kage.dto.request.PillarCreateRequest;
import com.kage.dto.request.PillarUpdateRequest;
import com.kage.dto.response.PillarResponse;
import com.kage.entity.Pillar;
import com.kage.entity.PillarTemplate;
import com.kage.entity.User;
import com.kage.enums.RecordStatus;
import com.kage.enums.UserStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.PillarMapper;
import com.kage.repository.PillarRepository;
import com.kage.repository.PillarTemplateRepository;
import com.kage.repository.UserRepository;
import com.kage.service.PillarService;
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
public class PillarServiceImpl implements PillarService {

    private final PillarRepository pillarRepository;
    private final PillarMapper pillarMapper;
    private final PillarTemplateRepository pillarTemplateRepository;
    private final UserRepository userRepository;

    /**
     * Create a new user pillar
     */
    @Override
    public PillarResponse create(PillarCreateRequest request, Long userId) {

        // 1️⃣ Sanitize input
//        String cleanName = SanitizerUtil.clean(request.getName());
//        String cleanDescription = SanitizerUtil.clean(request.getDescription());
//
//        log.debug("Sanitized user pillar name={}", cleanName);

        PillarTemplate pillarTemplate = pillarTemplateRepository
                .findByIdAndStatus(request.getPillarTemplateId(), RecordStatus.ACTIVE)
                .orElseThrow(() -> {
                    log.warn(" Master pillar not found with id={}", request.getPillarTemplateId());
                    return new NotFoundException("Master pillar not found");
                });


        User user = userRepository
                .findByIdAndUserStatusAndStatus(userId, UserStatus.ACTIVE,RecordStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // 3️⃣ Map DTO → Entity
        Pillar pillar = pillarMapper.toEntity(request);
        pillar.setName(pillarTemplate.getName());
        pillar.setDescription(pillarTemplate.getDescription());
        pillar.setPillarTemplate(pillarTemplate);
        pillar.setActive(true);
        pillar.setUser(user);

        // 4️⃣ Persist
        Pillar saved = pillarRepository.save(pillar);

        log.info("User pillar created successfully with id={}", saved.getId());

        // 5️⃣ Map Entity → DTO
        return pillarMapper.toResponse(saved);
    }

    /**
     * Get user pillar by id
     */
    @Override
    @Transactional(readOnly = true)
    public PillarResponse getById(Long id) {

        log.debug("Fetching user pillar with id={}", id);

        Pillar pillar = pillarRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    log.warn("User pillar not found with id={}", id);
                    return new NotFoundException("User pillar not found");
                });

        return pillarMapper.toResponse(pillar);
    }

    /**
     * Get all active user pillars
     */
    @Override
    @Transactional(readOnly = true)
    public List<PillarResponse> getAll() {

        log.debug("Fetching all active user pillars");

        return pillarRepository.findByActiveTrue()
                .stream()
                .map(pillarMapper::toResponse)
                .toList();
    }

    /**
     * Update user pillar
     */
    @Override
    public PillarResponse update(Long id, PillarUpdateRequest request) {

        log.debug("Updating user pillar with id={}", id);

        Pillar pillar = pillarRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update. User pillar not found with id={}", id);
                    return new NotFoundException("User pillar not found");
                });

        // 1️⃣ Sanitize inputs
        String cleanName = SanitizerUtil.clean(request.getName());
        String cleanDescription = SanitizerUtil.clean(request.getDescription());

        // 2️⃣ Business rule: unique name (only if changed)
        if (!pillar.getName().equalsIgnoreCase(cleanName)
                && pillarRepository.existsByNameIgnoreCase(cleanName)) {

            log.warn("Duplicate user pillar name during update: {}", cleanName);
            throw new BusinessException("Another user pillar already uses this name");
        }

        // 3️⃣ Map updates (MapStruct)
        pillarMapper.updateEntityFromDto(request, pillar);
        pillar.setName(cleanName);
        pillar.setDescription(cleanDescription);

        // 4️⃣ Save
        Pillar updated = pillarRepository.save(pillar);

        log.info("User pillar updated successfully with id={}", updated.getId());

        return pillarMapper.toResponse(updated);
    }

    /**
     * Soft delete (deactivate)
     */
    @Override
    public void deactivate(Long id) {

        log.debug("Deactivating user pillar with id={}", id);

        Pillar pillar = pillarRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    log.warn("Cannot deactivate. User pillar not found with id={}", id);
                    return new NotFoundException("User pillar not found");
                });

        pillar.setActive(false);
        pillarRepository.save(pillar);

        log.info("User pillar deactivated successfully with id={}", id);
    }
}
