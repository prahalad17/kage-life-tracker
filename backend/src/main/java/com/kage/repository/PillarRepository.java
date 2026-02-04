package com.kage.repository;

import com.kage.entity.Pillar;
import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PillarRepository extends JpaRepository<Pillar, Long> {


    Optional<Pillar> findByIdAndStatus(Long id, RecordStatus recordStatus);

    Optional<Pillar> findByIdAndUserIdAndStatus(Long id, Long userId, RecordStatus recordStatus);
}