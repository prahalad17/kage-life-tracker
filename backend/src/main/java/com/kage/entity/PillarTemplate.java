package com.kage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.kage.util.DomainGuardsUtil.requireNonEmpty;

@Entity
@Table(
        name = "pillar_templates",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"})}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PillarTemplate extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @Column()
    private String description;

    @Column(name = "priority_weight")
    private Integer priorityWeight;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(length = 30)
    private String color;

    protected PillarTemplate(String name, String description) {
        this.name = requireNonEmpty(name, "name is required");
        this.description = description;
    }

    public static PillarTemplate create(String name, String description) {
        return new PillarTemplate(name, description);
    }

    public void rename(String name) {

        this.name = requireNonEmpty(name, "name is required");
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
