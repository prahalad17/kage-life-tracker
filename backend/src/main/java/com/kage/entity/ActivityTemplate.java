package com.kage.entity;

import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import jakarta.persistence.*;
import lombok.*;
import static com.kage.util.DomainGuardsUtil.*;

@Entity
@Table(
        name = "activity_templates",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"pillar_template_id", "name"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityTemplate extends BaseEntity {

    /**
     * Life area this activity belongs to
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pillar_template_id", nullable = false)
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
    @Column(nullable = false, length = 30)
    private ActivityNature nature;

    /**
     * Default way of tracking this activity
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "default_tracking_type", nullable = false, length = 30)
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

    protected ActivityTemplate(
            PillarTemplate pillarTemplate,
            String name,
            ActivityNature nature,
            TrackingType defaultTrackingType,
            String defaultUnit,
            String description
    ) {
        this.pillarTemplate = requireNonNull(pillarTemplate, "pillarTemplate is required");
        this.name = requireNonEmpty(name, "name is required");
        this.nature = requireNonNull(nature, "nature is required");
        this.defaultTrackingType = requireNonNull(defaultTrackingType, "tracking type is required");
        this.defaultUnit = normalize(defaultUnit);
        this.description = normalize(description);
    }

    public static ActivityTemplate create(
            PillarTemplate pillarTemplate,
            String name,
            ActivityNature nature,
            TrackingType defaultTrackingType,
            String defaultUnit,
            String description
    ) {
        return new ActivityTemplate(
                pillarTemplate,
                name,
                nature,
                defaultTrackingType,
                defaultUnit,
                description
        );
    }

    /* -------- Controlled mutation -------- */

    public void updateDescription(String description) {
        this.description = normalize(description);
    }

    public void changeDefaultTracking(
            TrackingType trackingType,
            String defaultUnit
    ) {
        this.defaultTrackingType =
                requireNonNull(trackingType, "tracking type is required");
        this.defaultUnit = normalize(defaultUnit);
    }
    public void changeTemplate(PillarTemplate pillarTemplate) {
        this.pillarTemplate = requireNonNull(pillarTemplate,"template is required");
    }

    public void rename(String name) {
        this.name = requireNonEmpty(name, "name is required");
    }
}