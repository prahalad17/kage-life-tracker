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
import java.util.List;

import static com.kage.util.DomainGuardsUtil.requireNonEmpty;
import static com.kage.util.DomainGuardsUtil.requireNonNull;

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
    private PlanSource actionPlanSource;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionPlanStatus actionPlanStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityNature nature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrackingType trackingType;

    @Column(length = 1000)
    private String notes;

    @OneToMany(
            mappedBy = "actionPlan",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ActionPlanAttribute> attributeList;


    protected ActionPlan(
            User user,
            String actionPlanName,
            ActionPlanStatus actionPlanStatus,
            ActivityNature nature,
            TrackingType trackingType
    ) {
        this.user = requireNonNull(user, "user is required");
        this.actionPlanName = requireNonEmpty(actionPlanName, "actionName is required");
        this.actionPlanStatus = requireNonNull(actionPlanStatus, "actionStatus is required");
        this.nature = requireNonNull(nature, "nature is required");
        this.trackingType = requireNonNull(trackingType, "trackingType is required");
    }

    public static ActionPlan create(
            User user,
            String actionName,
            ActionPlanStatus actionStatus,
            ActivityNature nature,
            TrackingType trackingType) {
        return new ActionPlan(user, actionName, actionStatus, nature, trackingType);
    }

    public void addActivity(Activity activity) {

        this.activity = requireNonNull(activity, "activity is required");
        this.pillar = requireNonNull(activity.getPillar(), "pillar is required");
    }

    public void addPillar(Pillar pillar) {
        if (this.activity != null) {
            throw new IllegalStateException("activity is already set");
        }
        this.pillar = requireNonNull(pillar, "pillar is required");
    }

}