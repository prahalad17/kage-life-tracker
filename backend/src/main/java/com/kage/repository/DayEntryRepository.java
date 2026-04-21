package com.kage.repository;

import com.kage.entity.DayEntry;
import com.kage.entity.User;
import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Optional;

public interface DayEntryRepository extends JpaRepository<DayEntry, Long>, JpaSpecificationExecutor<DayEntry> {
    boolean existsByDateAndUserAndStatus(LocalDate date, User user, RecordStatus recordStatus);

    Optional<DayEntry> findByIdAndStatus(Long dayEntryId, RecordStatus recordStatus);

    Optional<DayEntry> findByDateAndUserAndStatus(LocalDate date, User user, RecordStatus recordStatus);

    Optional<DayEntry> findByDateAndUserAndStatus(LocalDate date, Long user, RecordStatus recordStatus);

    Optional<DayEntry> findByIdAndUserIdAndStatus(Long dayEntryId, Long userId, RecordStatus recordStatus);

    Optional<DayEntry> findByDateAndUserIdAndStatus(LocalDate date, User user, RecordStatus recordStatus);
}