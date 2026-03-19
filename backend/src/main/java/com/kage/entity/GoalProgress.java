package com.kage.entity;

import com.kage.enums.GoalStatus;
import com.kage.enums.GoalType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "goal_progress",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"goal_id", "date"}
                )
        },
        indexes = {
                @Index(name = "idx_goal_progress_goal", columnList = "goal_id"),
                @Index(name = "idx_goal_progress_date", columnList = "date")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoalProgress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;

    @Column(nullable = false)
    private LocalDate date;

    private BigDecimal progressPercent;

    private Integer intValue;
    private BigDecimal decimalValue;
    private Boolean booleanValue;

    @Column(length = 1000)
    private String textValue;

    @Column(length = 1000)
    private String notes;
}