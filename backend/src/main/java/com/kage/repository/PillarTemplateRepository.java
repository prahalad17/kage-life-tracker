package com.kage.repository;

import com.kage.entity.PillarTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PillarTemplateRepository extends JpaRepository<PillarTemplate, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<PillarTemplate> findByIdAndActiveTrue(Long id);

    List<PillarTemplate> findByActiveTrue();
}