package com.kage.repository;

import com.kage.entity.Pillar;
import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PillarRepository extends JpaRepository<Pillar, Long> , JpaSpecificationExecutor<Pillar> {


    Optional<Pillar> findByIdAndStatus(Long id, RecordStatus recordStatus);

    Optional<Pillar> findByIdAndUserIdAndStatus(Long id, Long userId, RecordStatus recordStatus);

   List<Pillar> findByUserIdAndStatus(Long userId, RecordStatus recordStatus);
}