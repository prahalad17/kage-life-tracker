package com.kage.entity;

import com.kage.enums.PlanItemType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "plan_item",
        indexes = {
                @Index(name = "idx_plan_item_plan", columnList = "plan_id"),
                @Index(name = "idx_plan_item_activity", columnList = "activity_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pillar_id")
    private Pillar pillar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pillar_metric_id")
    private PillarMetric pillarMetric;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanItemType type;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Column
    private Double targetValue; // e.g., 50 runs, 65 kg target

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;
}