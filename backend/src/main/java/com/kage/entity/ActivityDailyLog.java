package com.kage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "daily_activity_logs",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_activity_id", "log_date"})
        }
)
@Getter
@Setter
public class ActivityDailyLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_activity_id", nullable = false)
    private Activity activity;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    /**
     * Actual value logged by user
     * (reps, minutes, count, etc.)
     */
    @Column
    private Integer actualValue;

    /**
     * For BOOLEAN tracking (did / did not)
     */
    @Column
    private Boolean completed;

    @Column(length = 255)
    private String notes;
}
