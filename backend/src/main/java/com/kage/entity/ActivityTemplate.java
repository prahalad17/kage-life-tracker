package com.kage.entity;

import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "activity_templates",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"master_pillar_id", "name"}
                )
        }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTemplate extends BaseEntity {

    /**
     * Life area this activity belongs to
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "master_pillar_id", nullable = false)
    private PillarTemplate pillarTemplate;

    /**
     * Activity name (Push-ups, Meditation, Avoid Smoking)
     */
    @Column(length = 100, nullable = false)
    private String name;

    /**
     * POSITIVE (do) or NEGATIVE (avoid)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityNature nature;

    /**
     * Default way of tracking this activity
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "default_tracking_type", nullable = false)
    private TrackingType defaultTrackingType;

    /**
     * Default unit (reps, minutes, pages)
     */
    @Column(length = 30)
    private String defaultUnit;

    /**
     * Optional description/help text
     */
    @Column()
    private String description;

    /**
     * Enable/disable recommendation
     */
    @Column(nullable = false)
    private boolean active = true;
}
