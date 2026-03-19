package com.kage.repository;

import com.kage.entity.DayEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayEntryRepository extends JpaRepository<DayEntry, Long> {
}