package com.kage.entity;

import jakarta.persistence.*;
import lombok.*;
import static com.kage.util.DomainGuardsUtil.*;

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

        protected Pillar(User user, String name) {
                this.user = requireNonNull(user, "user is required");
                this.name = requireNonEmpty(name, "name is required");
        }
        public static Pillar create(User user, String name) {
                return new Pillar(user, name);
        }

        public void assignTemplate(PillarTemplate template) {
                this.pillarTemplate = template;
        }

        public void rename(String name) {
                this.name = requireNonEmpty(name, "name is required");
        }

        public void updateDescription(String description) {
                this.description = description;
        }


}