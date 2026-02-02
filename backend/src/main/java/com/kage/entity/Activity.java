package com.kage.entity;

import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "user_activities",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "user_pillar_id", "name"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends BaseEntity {

    /**
     * Owner of this activity
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;

    /**
     * User life area context
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_pillar_id", nullable = false)
    private Pillar pillar;

    /**
     * Optional reference to template
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_template_id")
    private ActivityTemplate activityTemplate;

    /**
     * User-facing name
     */
    @Column(length = 100, nullable = false)
    private String name;

    /**
     * POSITIVE / NEGATIVE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityNature nature;

    /**
     * How this activity is tracked
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrackingType trackingType;

    /**
     * Unit for tracking (reps, minutes, pages)
     */
    @Column(length = 30)
    private String unit;

    /**
     * Enable/disable activity
     */
    @Column(nullable = false)
    private boolean active = true;

    @Column(length = 255)
    private String description;
}
