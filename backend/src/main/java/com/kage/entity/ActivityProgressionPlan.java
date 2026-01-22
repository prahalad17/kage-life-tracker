package com.kage.entity;

import com.kage.enums.ProgressStrategy;
import com.kage.enums.RecalculationMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "activity_progression_plans",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"activity_progression_id"})
        }
)
@Getter
@Setter
public class ActivityProgressionPlan extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_progression_id", nullable = false)
    private ActivityProgression activityProgression;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgressStrategy strategy; // LINEAR, CUSTOM

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecalculationMode recalculationMode; // STRICT / ADAPTIVE

    @Column(nullable = false)
    private boolean active = true;
}
