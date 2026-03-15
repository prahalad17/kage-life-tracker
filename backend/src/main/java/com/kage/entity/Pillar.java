package com.kage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.kage.util.DomainGuardsUtil.requireNonEmpty;
import static com.kage.util.DomainGuardsUtil.requireNonNull;

@Entity
@Table(
        name = "pillar",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "name"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pillar extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pillar_template_id")
    private PillarTemplate pillarTemplate;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Column()
    private Integer priorityWeight;

    @Column()
    private Integer orderIndex;

    @Column(length = 30)
    private String color;

    protected Pillar(User user, String name) {
        this.user = requireNonNull(user, "user is required");
        this.name = requireNonEmpty(name, "name is required");
    }

    public static Pillar create(User user, String name) {
        return new Pillar(user, name);
    }

    public void assignTemplate(PillarTemplate template) {
        if (this.pillarTemplate != null) {
            throw new IllegalStateException("Template already assigned");
        }
        this.pillarTemplate = template;
    }

    public void rename(String name) {
        this.name = requireNonEmpty(name, "name is required");
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void setPriorityWeight(Integer weight) {
        if (weight != null && (weight < 1 || weight > 10)) {
            throw new IllegalArgumentException("priority weight must be between 1 and 10");
        }
        this.priorityWeight = weight;
    }

    public void setOrderIndex(Integer orderIndex) {
        if (orderIndex != null && orderIndex < 0) {
            throw new IllegalArgumentException("order index must be positive");
        }
        this.orderIndex = orderIndex;
    }


}