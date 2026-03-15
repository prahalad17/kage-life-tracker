package com.kage.entity;

import com.kage.enums.ActionPlanStatus;
import com.kage.enums.PlanSource;
import com.kage.enums.LogStatus;
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
public class ActionPlan extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Business date (used for streaks & reports)
     */
    @Column(name = "plan_date", nullable = false)
    private LocalDate planDate;

    /**
     * Log Source tracking (system , user)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanSource planSource;

    /**
     * Log Status tracking
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionPlanStatus planStatus;


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

    protected ActionPlan(
            Activity activity,
            User user,
            LocalDate logDate,
            Integer actualValue,
            Boolean completed,
            String notes,
            LogStatus logStatus,
            PlanSource planSource
    ) {
        this.activity = requireNonNull(activity, "activity is required");
        this.user = requireNonNull(user, "user is required");

        validateOwnership(activity, user);
        validateTrackingValues(actualValue, completed);

        this.actualValue = actualValue;
        this.completed = completed;
        this.notes = normalize(notes);
        this.planSource = planSource;
    }

    /* -------- Factory -------- */

    public static ActionPlan create(
            Activity activity,
            User user,
            LocalDate logDate,
            Integer actualValue,
            Boolean completed,
            String notes
    ) {
        return new ActionPlan(
                activity,
                user,
                logDate,
                actualValue,
                completed,
                notes,
                LogStatus.PENDING,
                PlanSource.USER_ENTRY
        );
    }

    public static ActionPlan createBaseline(
            Activity activity,
            User user,
            LocalDate logDate
    ) {
        return new ActionPlan(
                activity,
                user,
                logDate,
                null,
                false,
                null,
                LogStatus.PENDING,
                PlanSource.SYSTEM_BASELINE
        );
    }

    /**
     * Default factory for "log now"
     */
    public static ActionPlan logNow(
            Activity activity,
            User user,
            Integer actualValue,
            Boolean completed,
            String notes
    ) {
        return new ActionPlan(
                activity,
                user,
                LocalDate.now(),
                actualValue,
                completed,
                notes,
                LogStatus.DONE,
                PlanSource.USER_ENTRY
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