package com.kage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "metric_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MetricLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metric_id", nullable = false)
    private Metric metric;

    @Column(nullable = false)
    private LocalDate loggedDate;

    @Column(nullable = false)
    private Double value;

    @Column(length = 500)
    private String notes;
}