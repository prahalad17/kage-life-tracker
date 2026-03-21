package com.kage.entity;

import com.kage.enums.PillarMetricType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pillar_metric",
        indexes = {
                @Index(name = "idx_metric_pillar", columnList = "pillar_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PillarMetric extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pillar_id", nullable = false)
    private Pillar pillar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PillarMetricType metricType;

    @Column
    private Double targetValue; // optional goal reference

    @Column(length = 255)
    private String description;
}