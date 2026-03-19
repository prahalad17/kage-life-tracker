package com.kage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pillar_metric_log",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"pillar_metric_id", "logged_date"}
                )
        },
        indexes = {
                @Index(name = "idx_metric_log_metric", columnList = "pillar_metric_id"),
                @Index(name = "idx_metric_log_date", columnList = "logged_date")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PillarMetricLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pillar_metric_id", nullable = false)
    private PillarMetric pillarMetric;

    @Column(nullable = false)
    private LocalDate loggedDate;

    @Column(nullable = false)
    private Double value;

    @Column(length = 500)
    private String notes;
}
