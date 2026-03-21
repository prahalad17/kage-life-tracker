package com.kage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.kage.util.DomainGuardsUtil.requireNonNull;

@Entity
@Table(name = "action_entry_attributes",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"action_entry_id", "attribute_definition_id"}
                )
        },
        indexes = {
                @Index(name = "idx_action_attr_entry", columnList = "action_entry_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionEntryAttribute extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "action_entry_id", nullable = false)
    private ActionEntry actionEntry;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_definition_id", nullable = false)
    private ActionAttributeDefinition attributeDefinition;

    private Integer intValue;
    private BigDecimal decimalValue;
    private Boolean booleanValue;

    @Column(length = 1000)
    private String textValue;

    protected ActionEntryAttribute(ActionEntry actionEntry, ActionAttributeDefinition attributeDefinition) {
        this.actionEntry = requireNonNull(actionEntry, "actionEntry is null");
        this.attributeDefinition = requireNonNull(attributeDefinition, "attributeDefinition is null");
    }

    public static ActionEntryAttribute create(ActionEntry actionEntry, ActionAttributeDefinition attributeDefinition) {

        return new ActionEntryAttribute(actionEntry, attributeDefinition);
    }
}