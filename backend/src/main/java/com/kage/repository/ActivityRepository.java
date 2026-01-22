package com.kage.repository;

import com.kage.entity.Activity;
import com.kage.entity.ActivityTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    boolean existsByNameIgnoreCase(String cleanName);

    Optional<Activity> findByIdAndActiveTrue(Long id);

    List<Activity> findByActiveTrue();
}