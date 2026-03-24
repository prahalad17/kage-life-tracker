package com.kage.entity;

import com.kage.enums.ActivityNature;
import com.kage.enums.ActivityType;
import com.kage.enums.ScheduleType;
import com.kage.enums.TrackingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Set;

import static com.kage.util.DomainGuardsUtil.*;

@Entity
@Table(
        name = "activity",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "pillar_id", "name"}
                )
        },
        indexes = {
                @Index(name = "idx_activity_user", columnList = "user_id"),
                @Index(name = "idx_activity_pillar", columnList = "pillar_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pillar_id")
    private Pillar pillar;

    @Column(length = 100, nullable = false)
    private String activityName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ActivityNature activityNature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TrackingType activityTrackingType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ActivityType activityType;

    @Column(length = 30)
    private String unit;

    @Column
    @Setter
    private String activityDescription;

    @OneToOne(
            mappedBy = "activity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private ActivitySchedule schedule;

    protected Activity(
            User user,
            String activityName,
            ActivityType activityType,
            ActivityNature activityNature,
            TrackingType activityTrackingType
    ) {
        this.user = requireNonNull(user, "user is required");
        this.activityName = requireNonEmpty(activityName, "name is required");
        this.activityNature = requireNonNull(activityNature, "nature is required");
        this.activityType = requireNonNull(activityType, "activityType is required");
        this.activityTrackingType =
                requireNonNull(activityTrackingType, "tracking type is required");
    }

    public static Activity create(
            User user,
            String activityName,
            ActivityType activityType,
            ActivityNature activityNature,
            TrackingType activityTrackingType

    ) {
        return new Activity(
                user,
                activityName,
                activityType,
                activityNature,
                activityTrackingType
        );
    }

    /* -------- Schedule ownership -------- */

    public void attachSchedule(ActivitySchedule schedule) {
        this.schedule = requireNonNull(schedule, "schedule is required");
        schedule.attachTo(this);
    }

    public void updateSchedule(ScheduleType type, Set<DayOfWeek> days) {
        this.schedule.changeSchedule(type, days);
    }

    public void addPillar(Pillar pillar) {
        this.pillar = requireNonNull(pillar, "pillar is required");
    }

    public void deactivateSchedule() {
        this.schedule.deactivate();
    }

    /* -------- Controlled mutation -------- */

    public void rename(String name) {
        this.activityName = requireNonEmpty(name, "name is required");
    }

    public void changeTracking(TrackingType trackingType) {
        this.activityTrackingType =
                requireNonNull(trackingType, "tracking type is required");
    }


    public void updateDescription(String description) {
        this.activityDescription = normalize(description);
    }

    public void updateNature(ActivityNature nature) {
        this.activityNature = nature;
    }
}
