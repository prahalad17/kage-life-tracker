package com.kage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_pillars",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"})}
)
public class Pillar extends BaseEntity {


        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_id",  nullable = false)
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "master_pillar_id")
        private PillarTemplate pillarTemplate;

        @Column()
        private Long userPillarId;

        @Column(length = 100, nullable = false)
        private String name;

        @Column(length = 255)
        private String description;

        @Column()
        private Boolean active;

}
