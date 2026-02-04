package com.kage.repository;

import com.kage.entity.Activity;
import com.kage.entity.ActivityTemplate;
import com.kage.entity.Pillar;
import com.kage.entity.User;
import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {


    boolean existsByUserAndPillarAndNameIgnoreCaseAndStatus(User user, Pillar pillar, String name, RecordStatus recordStatus);

    Optional<Activity> findByIdAndStatus(Long id, RecordStatus recordStatus);

    List<Activity> findByUserIdAndStatus(Long userId, RecordStatus recordStatus);
}