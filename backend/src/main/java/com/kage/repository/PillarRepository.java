package com.kage.repository;

import com.kage.entity.Pillar;
import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PillarRepository extends JpaRepository<Pillar, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Pillar> findByIdAndActiveTrue(Long id);

    List<Pillar> findByActiveTrue();

    Optional<Pillar> findByIdAndStatus(Long id, RecordStatus recordStatus);
}