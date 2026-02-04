package com.kage.repository;

import com.kage.entity.ActivitySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityScheduleRepository extends JpaRepository<ActivitySchedule, Long> {
}