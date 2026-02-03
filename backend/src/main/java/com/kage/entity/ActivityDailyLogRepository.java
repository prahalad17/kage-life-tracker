package com.kage.entity;

import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface ActivityDailyLogRepository extends JpaRepository<ActivityDailyLog, Long> {
    Optional<ActivityDailyLog> findByIdAndStatus(Long id, RecordStatus recordStatus);

    List< ActivityDailyLog>findByStatus(RecordStatus recordStatus);
}