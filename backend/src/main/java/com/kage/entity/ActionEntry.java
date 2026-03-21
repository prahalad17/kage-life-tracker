package com.kage.entity;

import com.kage.enums.ActionStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

import static com.kage.util.DomainGuardsUtil.requireNonEmpty;
import static com.kage.util.DomainGuardsUtil.requireNonNull;

@Entity
@Table(name = "action_entry",
        indexes = {
                @Index(name = "idx_action_user_time", columnList = "user_id, logged_at"),
                @Index(name = "idx_action_day", columnList = "day_entry_id"),
                @Index(name = "idx_action_activity", columnList = "activity_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionEntry extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pillar_id")
    private Pillar pillar;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_plan_id")
    private ActionPlan actionPlan;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "day_entry_id", nullable = false)
    private DayEntry dayEntry;

    @Column(name = "logged_at", nullable = false)
    private Instant loggedAt;

    @Column(length = 100, nullable = false)
    private String actionName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionStatus actionStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityNature nature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrackingType trackingType;

    @Column(length = 1000)
    private String notes;

    @OneToMany(
            mappedBy = "actionEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ActionEntryAttribute> attributeList;


    protected ActionEntry(
            DayEntry dayEntry,
            User user,
            String actionName,
            ActionStatus actionStatus,
            ActivityNature nature,
            TrackingType trackingType
    ) {
        this.dayEntry = requireNonNull(dayEntry, "dayEntry is required");
        this.user = requireNonNull(user, "user is required");
        this.actionName = requireNonEmpty(actionName, "actionName is required");
        this.actionStatus = requireNonNull(actionStatus, "actionStatus is required");
        this.nature = requireNonNull(nature, "nature is required");
        this.trackingType = requireNonNull(trackingType, "trackingType is required");
        this.loggedAt = Instant.now();
    }

    public static ActionEntry create(
            DayEntry dayEntry,
            User user,
            String actionName,
            ActionStatus actionStatus,
            ActivityNature nature,
            TrackingType trackingType) {
        return new ActionEntry(dayEntry, user, actionName, actionStatus, nature, trackingType);
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

    public void addActionPlan(ActionPlan actionPlan) {
        this.actionPlan = requireNonNull(actionPlan, "actionPlan is required");
    }
}