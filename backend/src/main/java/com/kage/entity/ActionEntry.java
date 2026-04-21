package com.kage.entity;

import com.kage.enums.ActionEntryStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
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

    @Column(name = "action_entry_date", nullable = false)
    private LocalDate actionEntryDate;

    @Column(length = 100, nullable = false)
    private String actionEntryName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionEntryStatus actionEntryStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityNature actionEntryNature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrackingType actionEntryTrackingType;

    @Setter
    @Column(length = 1000)
    private String actionEntryNotes;

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
            ActionEntryStatus actionEntryStatus,
            ActivityNature nature,
            TrackingType trackingType
    ) {
        this.dayEntry = requireNonNull(dayEntry, "dayEntry is required");
        this.user = requireNonNull(user, "user is required");
        this.actionEntryName = requireNonEmpty(actionName, "actionName is required");
        this.actionEntryStatus = requireNonNull(actionEntryStatus, "actionEntryStatus is required");
        this.actionEntryNature = requireNonNull(nature, "nature is required");
        this.actionEntryTrackingType = requireNonNull(trackingType, "trackingType is required");
        this.actionEntryDate = requireNonNull(dayEntry.getDate(), "actionEntryDate is required");
        this.loggedAt = Instant.now();
    }

    public static ActionEntry create(
            DayEntry dayEntry,
            User user,
            String actionName,
            ActionEntryStatus actionEntryStatus,
            ActivityNature nature,
            TrackingType trackingType) {
        return new ActionEntry(dayEntry, user, actionName, actionEntryStatus, nature, trackingType);
    }

    public void addActivity(Activity activity) {

        this.activity = requireNonNull(activity, "activity is required");
        this.pillar = activity.getPillar();
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