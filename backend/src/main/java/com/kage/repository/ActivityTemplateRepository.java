package com.kage.repository;

import com.kage.entity.ActivityTemplate;
import com.kage.entity.PillarTemplate;
import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ActivityTemplateRepository extends JpaRepository<ActivityTemplate, Long> {
    boolean existsByNameIgnoreCase(String cleanName);



    Optional<ActivityTemplate>  findByIdAndStatus(Long activityId, RecordStatus recordStatus);

    boolean existsByPillarTemplateAndNameIgnoreCaseAndStatus(PillarTemplate pillarTemplate, String name, RecordStatus recordStatus);

    List<ActivityTemplate> findByStatus(RecordStatus recordStatus);
}