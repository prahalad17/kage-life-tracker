package com.kage.entity;

import com.kage.enums.ActionPlanStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.PlanSource;
import com.kage.enums.TrackingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "action_plan",
        indexes = {
                @Index(name = "idx_plan_user", columnList = "user_id"),
                @Index(name = "idx_plan_activity", columnList = "activity_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionPlan extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pillar_id")
    private Pillar pillar;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "planned_date")
    private LocalDate plannedDate;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Column(nullable = false, length = 100)
    private String actionPlanName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanSource planSource;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionPlanStatus planStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityNature nature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrackingType trackingType;

    @Column(length = 1000)
    private String notes;
}