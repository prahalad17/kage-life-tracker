package com.kage.entity;

import com.kage.enums.ActionAttributes;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "action_entry_attributes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionEntryAttributes extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_entry_id")
    private ActionEntry actionEntry;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionAttributes attribute;

    @Column(length = 100, nullable = false)
    private String attributeValue;

    @Column
    private String notes;

    /* -------- Constructor -------- */
    protected ActionEntryAttributes(ActionEntry actionEntry, ActionAttributes attribute, String attributeValue, String notes) {
        this.actionEntry = actionEntry;
        this.attribute = attribute;
        this.attributeValue = attributeValue;
        this.notes = notes;
    }


}