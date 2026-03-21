package com.kage.repository;

import com.kage.entity.ActionEntry;
import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ActionEntryRepository extends JpaRepository<ActionEntry, Long>, JpaSpecificationExecutor<ActionEntry> {


    Optional<ActionEntry> findByIdAndUserIdAndStatus(Long actionEntryId, Long userId, RecordStatus recordStatus);
}