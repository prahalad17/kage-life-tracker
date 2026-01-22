package com.kage.entity;

import com.kage.enums.ProgressDirection;
import com.kage.enums.ProgressStrategy;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_progressions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_activity_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityProgression extends BaseEntity {

    /**
     * Activity this progression belongs to
     */
    @ManyToOne
    @JoinColumn(name = "activity_user_id")
    private Activity activity;

    /**
     * Starting value (e.g. 0 pushups, 2 cigarettes)
     */
    @Column(name = "start_value", nullable = false)
    private Integer startValue;

    /**
     * Target value (e.g. 100 pushups, 0 cigarettes)
     */
    @Column(name = "target_value", nullable = false)
    private Integer targetValue;

    /**
     * Unit (reps, minutes, cigarettes)
     */
    @Column(length = 30)
    private String unit;

    /**
     * INCREASE or DECREASE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgressDirection direction;

    /**
     * LINEAR, MANUAL, CUSTOM (future)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgressStrategy strategy;

    /**
     * Whether progression is active
     */
    @Column(nullable = false)
    private boolean active = true;

    /**
     * When progression was completed
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
