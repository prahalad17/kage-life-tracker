package com.kage.entity;

import com.kage.enums.LogSource;
import com.kage.enums.LogStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.kage.util.DomainGuardsUtil.*;
@Entity
@Table(name = "activity_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityDailyLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Business date (used for streaks & reports)
     */
    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    /**
     * Exact execution timestamp (system-generated)
     */
    @Column(name = "logged_at", nullable = false, updatable = false)
    private Instant loggedAt;

    /**
     * Log Source tracking (system , user)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogSource logSource;

    /**
     * Log Status tracking
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogStatus logStatus;


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

    protected ActivityDailyLog(
            Activity activity,
            User user,
            LocalDate logDate,
            Integer actualValue,
            Boolean completed,
            String notes,
            LogStatus logStatus,
            LogSource logSource
    ) {
        this.activity = requireNonNull(activity, "activity is required");
        this.user = requireNonNull(user, "user is required");
        this.logDate = requireNonNull(logDate, "log date is required");
        this.loggedAt = Instant.now();

        validateOwnership(activity, user);
        validateTrackingValues(actualValue, completed);

        this.actualValue = actualValue;
        this.completed = completed;
        this.notes = normalize(notes);
        this.logStatus = logStatus;
        this.logSource = logSource;
    }

    /* -------- Factory -------- */

    public static ActivityDailyLog create(
            Activity activity,
            User user,
            LocalDate logDate,
            Integer actualValue,
            Boolean completed,
            String notes
    ) {
        return new ActivityDailyLog(
                activity,
                user,
                logDate,
                actualValue,
                completed,
                notes,
                LogStatus.PENDING,
                LogSource.USER_ENTRY
        );
    }

    public static ActivityDailyLog createBaseline(
            Activity activity,
            User user,
            LocalDate logDate
    ) {
        return new ActivityDailyLog(
                activity,
                user,
                logDate,
                null,
                false,
                null,
                LogStatus.PENDING,
                LogSource.SYSTEM_BASELINE
        );
    }

    /**
     * Default factory for "log now"
     */
    public static ActivityDailyLog logNow(
            Activity activity,
            User user,
            Integer actualValue,
            Boolean completed,
            String notes
    ) {
        return new ActivityDailyLog(
                activity,
                user,
                LocalDate.now(),
                actualValue,
                completed,
                notes,
                LogStatus.DONE,
                LogSource.USER_ENTRY
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
