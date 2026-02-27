package com.kage.repository;

import com.kage.entity.ActivityDailyLog;
import com.kage.enums.LogSource;
import com.kage.enums.LogStatus;
import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface ActivityDailyLogRepository extends JpaRepository<ActivityDailyLog, Long> , JpaSpecificationExecutor<ActivityDailyLog> {

    Optional<ActivityDailyLog> findByIdAndStatus(Long id, RecordStatus status);

    List<ActivityDailyLog> findByUserIdAndStatus(Long userId, RecordStatus status);

    boolean existsByActivityIdAndUserIdAndLogDateAndLogSource(Long id, Long id1, LocalDate date, LogSource logSource);

    List<ActivityDailyLog> findByUserIdAndLogDateAndCompletedAndStatus(Long userId, LocalDate date, boolean b, RecordStatus recordStatus);
}