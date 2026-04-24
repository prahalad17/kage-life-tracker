package com.kage.entity;

import com.kage.enums.ActionPlanStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.PlanSource;
import com.kage.enums.TrackingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    @Column(name = "action_plan_date")
    private LocalDate actionPlanDate;

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
    private ActivityNature actionPlanNature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrackingType actionPlanTrackingType;

    @Setter
    @Column(length = 1000)
    private String actionPlanNotes;

    @OneToMany(
            mappedBy = "actionPlan",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ActionPlanAttribute> actionPlanAttributeList;


    protected ActionPlan(
            User user,
            String actionPlanName,
            LocalDate actionPlanDate,
            ActionPlanStatus actionPlanStatus,
            ActivityNature nature,
            TrackingType trackingType
    ) {
        this.user = requireNonNull(user, "user is required");
        this.actionPlanName = requireNonEmpty(actionPlanName, "actionName is required");
        this.actionPlanDate = requireNonNull(actionPlanDate, "actionPlanDate is required");
        this.actionPlanStatus = requireNonNull(actionPlanStatus, "actionStatus is required");
        this.actionPlanNature = requireNonNull(nature, "nature is required");
        this.actionPlanTrackingType = requireNonNull(trackingType, "trackingType is required");
        this.actionPlanSource = PlanSource.USER_ENTRY;
    }

    protected ActionPlan(
            User user,
            String actionPlanName,
            LocalDate actionPlanDate,
            ActionPlanStatus actionPlanStatus,
            ActivityNature nature,
            TrackingType trackingType,
            PlanSource planSource
    ) {
        this.user = requireNonNull(user, "user is required");
        this.actionPlanName = requireNonEmpty(actionPlanName, "actionName is required");
        this.actionPlanDate = requireNonNull(actionPlanDate, "actionPlanDate is required");
        this.actionPlanStatus = requireNonNull(actionPlanStatus, "actionStatus is required");
        this.actionPlanNature = requireNonNull(nature, "nature is required");
        this.actionPlanTrackingType = requireNonNull(trackingType, "trackingType is required");
        this.actionPlanSource = planSource;
    }

    protected ActionPlan(
            User user,
            Activity activity,
            LocalDate actionPlanDate
    ) {
        this.user = requireNonNull(user, "user is required");
        this.activity = requireNonNull(activity, "activity is required");
        this.actionPlanName = requireNonEmpty(activity.getActivityName(), "actionName is required");
        this.actionPlanDate = requireNonNull(actionPlanDate, "actionPlanDate is required");
        this.actionPlanStatus = ActionPlanStatus.SCHEDULED;
        this.actionPlanNature = requireNonNull(activity.getActivityNature(), "nature is required");
        this.actionPlanTrackingType = requireNonNull(activity.getActivityTrackingType(), "trackingType is required");
        this.actionPlanSource = PlanSource.SYSTEM_BASELINE;
    }

    public static ActionPlan createUserActionPlan(
            User user,
            String actionName,
            LocalDate actionPlanDate,
            ActionPlanStatus actionStatus,
            ActivityNature nature,
            TrackingType trackingType) {
        return new ActionPlan(user, actionName, actionPlanDate, actionStatus, nature, trackingType, PlanSource.USER_ENTRY);
    }

    public static ActionPlan createSystemActionPlan(
            User user,
            Activity activity,
            LocalDate actionPlanDate) {
        return new ActionPlan(user, activity, actionPlanDate);
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

    public void completePlan() {

        this.actionPlanStatus = ActionPlanStatus.COMPLETED;

    }

}