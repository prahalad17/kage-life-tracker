package com.kage.entity;

import com.kage.enums.MetricType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "metric")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Metric extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pillar_id")
    private Pillar pillar;

    @Column(length = 100, nullable = false)
    private String metricName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetricType metricType;

    @Column
    private Double currentValue;

    @Column
    private Double targetValue; // optional goal reference

    @Column(length = 255)
    private String description;
}