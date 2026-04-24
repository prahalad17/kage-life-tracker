package com.kage.entity;

import com.kage.enums.ScheduleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.Set;

import static com.kage.util.DomainGuardsUtil.requireNonNull;

@Entity
@Table(name = "activity_schedule",
        indexes = {
                @Index(name = "idx_schedule_activity", columnList = "activity_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivitySchedule extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_id", nullable = false, unique = true)
    private Activity activity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ScheduleType type;

    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.LAZY)
    @CollectionTable(
            name = "activity_schedule_days",
            joinColumns = @JoinColumn(name = "schedule_id"),
            indexes = {
                    @Index(name = "idx_schedule_days", columnList = "schedule_id")
            }
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false, length = 15)
    private Set<DayOfWeek> days;


    protected ActivitySchedule(
            Activity activity,
            ScheduleType type,
            Set<DayOfWeek> days
    ) {
        this.activity =
                requireNonNull(activity, "activity is required");
        this.type =
                requireNonNull(type, "schedule type is required");

        validateDays(type, days);
        this.days = days;
    }

    public static ActivitySchedule create(
            Activity activity,
            ScheduleType type,
            Set<DayOfWeek> days
    ) {
        return new ActivitySchedule(activity, type, days);
    }

    /* -------- Domain behavior -------- */

    void attachTo(Activity activity) {
        this.activity = activity;
    }

    public void changeSchedule(ScheduleType type, Set<DayOfWeek> days) {
        this.type = requireNonNull(type, "schedule type is required");
        validateDays(type, days);
        this.days = days;
    }

    public boolean isScheduledOn(DayOfWeek day) {
        return switch (type) {
            case DAILY -> true;
            case WEEKDAYS -> day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
            case WEEKENDS -> day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
            case ONE_TIME -> day == DayOfWeek.FRIDAY;
            case CUSTOM -> days != null && days.contains(day);
        };
    }

    /* -------- validation -------- */

    private static void validateDays(
            ScheduleType type,
            Set<DayOfWeek> days
    ) {
        if (type == ScheduleType.CUSTOM) {
            if (days == null || days.isEmpty()) {
                throw new IllegalArgumentException(
                        "CUSTOM schedule requires at least one day"
                );
            }
        } else {
            if (days != null && !days.isEmpty()) {
                throw new IllegalArgumentException(
                        "Days are only allowed for CUSTOM schedules"
                );
            }
        }
    }
}