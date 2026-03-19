package com.kage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "day_metric",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"day_entry_id", "day_metric_definition_id"}
                )
        },
        indexes = {
                @Index(name = "idx_day_metric_day", columnList = "day_entry_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DayMetric extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "day_entry_id", nullable = false)
    private DayEntry dayEntry;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "day_metric_definition_id", nullable = false)
    private DayMetricDefinition dayMetricDefinition;

    private Integer intValue;

    private BigDecimal decimalValue;

    private Boolean booleanValue;

    private String textValue;

    //  later: add validation method
}
