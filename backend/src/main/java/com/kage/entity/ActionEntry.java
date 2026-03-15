package com.kage.entity;

import com.kage.enums.ActionStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

import static com.kage.util.DomainGuardsUtil.normalize;
import static com.kage.util.DomainGuardsUtil.requireNonNull;

@Entity
@Table(name = "action_entry")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionEntry extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Exact execution timestamp (system-generated)
     */
    @Column(name = "logged_at", nullable = false)
    private Instant loggedAt;

    /**
     * Business date (used for streaks & reports)
     */
    @Column(name = "action_date", nullable = false)
    private LocalDate actionDate;

    @Column(length = 100, nullable = false)
    private String actionName;

    /**
     * Action Status tracking
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionStatus actionStatus;

    /**
     * POSITIVE / NEGATIVE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ActivityNature nature;

    /**
     * How this activity is tracked
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TrackingType trackingType;

    /**
     * Numeric tracking (reps, minutes, etc.)
     */
    @Column
    private Integer actualValue;

    /**
     * Boolean tracking (did / did not)
     */
    @Column
    private Boolean completed;

    @Column
    private String notes;

    /* -------- Constructor -------- */

    protected ActionEntry(
            Activity activity,
            User user,
            LocalDate actionDate,
            Integer actualValue,
            Boolean completed,
            String notes

    ) {
        this.activity = requireNonNull(activity, "activity is required");
        this.user = requireNonNull(user, "user is required");
        this.actionDate = requireNonNull(actionDate, "log date is required");
        this.loggedAt = Instant.now();

        validateOwnership(activity, user);
        validateTrackingValues(actualValue, completed);

        this.actualValue = actualValue;
        this.completed = completed;
        this.notes = normalize(notes);
    }

    /* -------- Factory -------- */

    public static ActionEntry create(
            Activity activity,
            User user,
            LocalDate logDate,
            Integer actualValue,
            Boolean completed,
            String notes
    ) {
        return new ActionEntry(
                activity,
                user,
                logDate,
                actualValue,
                completed,
                notes
        );
    }

    public static ActionEntry createBaseline(
            Activity activity,
            User user,
            LocalDate logDate
    ) {
        return new ActionEntry(
                activity,
                user,
                logDate,
                null,
                false,
                null
        );
    }

    /**
     * Default factory for "log now"
     */
    public static ActionEntry logNow(
            Activity activity,
            User user,
            Integer actualValue,
            Boolean completed,
            String notes
    ) {
        return new ActionEntry(
                activity,
                user,
                LocalDate.now(),
                actualValue,
                completed,
                notes
        );
    }

    /* -------- Domain behavior -------- */

    public void updateTracking(
            Integer actualValue,
            Boolean completed,
            String notes
    ) {
        validateTrackingValues(actualValue, completed);
        this.actualValue = actualValue;
        this.completed = completed;
        this.notes = normalize(notes);
    }

    /* -------- Validation -------- */

    private static void validateTrackingValues(
            Integer actualValue,
            Boolean completed
    ) {
        if (actualValue != null && completed != null) {
            throw new IllegalArgumentException(
                    "Provide either actualValue or completed, not both"
            );
        }
        if (actualValue == null && completed == null) {
            throw new IllegalArgumentException(
                    "Either actualValue or completed must be provided"
            );
        }
    }

    private static void validateOwnership(
            Activity activity,
            User user
    ) {
        if (!activity.getUser().equals(user)) {
            throw new IllegalArgumentException(
                    "User does not own this activity"
            );
        }
    }
}