package com.kage.repository;

import com.kage.entity.ActionPlan;
import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ActionPlanRepository extends JpaRepository<ActionPlan, Long>, JpaSpecificationExecutor<ActionPlan> {


    Optional<ActionPlan> findByIdAndUserIdAndStatus(Long actionPlanId, Long userId, RecordStatus recordStatus);
}