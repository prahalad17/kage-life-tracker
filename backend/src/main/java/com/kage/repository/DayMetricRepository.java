package com.kage.repository;

import com.kage.entity.DayMetric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayMetricRepository extends JpaRepository<DayMetric, Long> {
}