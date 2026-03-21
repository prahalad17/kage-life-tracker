package com.kage.entity;

import com.kage.enums.ActivityNature;
import com.kage.enums.ActivityType;
import com.kage.enums.ScheduleType;
import com.kage.enums.TrackingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ActivityNature nature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TrackingType trackingType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ActivityType activityType;

    @Column(length = 30)
    private String unit;

    @Column
    private String description;

    @OneToOne(
            mappedBy = "activity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private ActivitySchedule schedule;

    protected Activity(
            User user,
            Pillar pillar,
            String name,
            ActivityNature nature,
            TrackingType trackingType,
            String unit,
            String description
    ) {
        this.user = requireNonNull(user, "user is required");
        this.pillar = requireNonNull(pillar, "pillar is required");
        this.name = requireNonEmpty(name, "name is required");
        this.nature = requireNonNull(nature, "nature is required");
        this.trackingType =
                requireNonNull(trackingType, "tracking type is required");
        this.unit = normalize(unit);
        this.description = normalize(description);
    }

    public static Activity create(
            User user,
            Pillar pillar,
            String name,
            ActivityNature nature,
            TrackingType trackingType,
            String unit,
            String description
    ) {
        return new Activity(
                user,
                pillar,
                name,
                nature,
                trackingType,
                unit,
                description
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

    public void deactivateSchedule() {
        this.schedule.deactivate();
    }

    /* -------- Controlled mutation -------- */

    public void rename(String name) {
        this.name = requireNonEmpty(name, "name is required");
    }

    public void changeTracking(TrackingType trackingType, String unit) {
        this.trackingType =
                requireNonNull(trackingType, "tracking type is required");
        this.unit = normalize(unit);
    }


    public void updateDescription(String description) {
        this.description = normalize(description);
    }

    public void updateNature(ActivityNature nature) {
        this.nature = nature;
    }
}
