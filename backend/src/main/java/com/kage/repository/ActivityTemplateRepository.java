package com.kage.repository;

import com.kage.entity.ActivityTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityTemplateRepository extends JpaRepository<ActivityTemplate, Long> {
    boolean existsByNameIgnoreCase(String cleanName);

    Optional<ActivityTemplate> findByIdAndActiveTrue(Long id);

    List<ActivityTemplate> findByActiveTrue();
}