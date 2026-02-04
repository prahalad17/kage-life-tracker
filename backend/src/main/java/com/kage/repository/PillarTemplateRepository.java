package com.kage.repository;

import com.kage.entity.PillarTemplate;
import com.kage.enums.RecordStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PillarTemplateRepository extends JpaRepository<PillarTemplate, Long> {

    Optional<PillarTemplate> findByIdAndStatus(Long id , RecordStatus recordStatus);

    List<PillarTemplate> findByActiveTrue();

    List<PillarTemplate> findByStatus(RecordStatus recordStatus);

    boolean existsByNameIgnoreCaseAndStatus(@NotBlank @Size(min = 3, max = 100) String name, RecordStatus recordStatus);
}