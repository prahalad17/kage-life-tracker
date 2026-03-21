package com.kage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "action_plan_attributes",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"action_plan_id", "attribute_definition_id"}
                )
        },
        indexes = {
                @Index(name = "idx_action_attr_plan", columnList = "action_plan_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionPlanAttribute extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "action_plan_id", nullable = false)
    private ActionPlan actionPlan;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_definition_id", nullable = false)
    private ActionAttributeDefinition attributeDefinition;

    private Integer intValue;
    private BigDecimal decimalValue;
    private Boolean booleanValue;

    @Column(length = 1000)
    private String textValue;
}