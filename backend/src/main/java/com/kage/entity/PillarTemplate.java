package com.kage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(
        name = "master_pillars",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"})}
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PillarTemplate extends BaseEntity {


    @Column(nullable = false)
    private Long masterPillarId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Column()
    private Boolean active;
}
