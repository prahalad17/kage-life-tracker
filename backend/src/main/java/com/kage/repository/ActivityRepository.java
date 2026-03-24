package com.kage.repository;

import com.kage.entity.Activity;
import com.kage.entity.Pillar;
import com.kage.entity.User;
import com.kage.enums.RecordStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {


    Optional<Activity> findByIdAndStatus(Long id, RecordStatus recordStatus);

    List<Activity> findByUserIdAndStatus(Long userId, RecordStatus recordStatus);

    List<Activity> findAllByStatus(RecordStatus recordStatus);

    boolean existsByUserAndPillarAndActivityNameIgnoreCaseAndStatus(User user, Pillar pillar, @NotBlank String s, RecordStatus recordStatus);

    boolean existsByUserAndActivityNameIgnoreCaseAndStatus(User user, @NotBlank String s, RecordStatus recordStatus);
}