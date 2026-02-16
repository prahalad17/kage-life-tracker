package com.kage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static com.kage.util.DomainGuardsUtil.requireNonEmpty;
import static com.kage.util.DomainGuardsUtil.requireNonNull;

@Entity
@Table(
        name = "verification_tokens",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"token"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationToken extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true, length = 150)
    private String token;

    @Column(name = "expiry_at", nullable = false)
    private Instant expiryAt;

    protected VerificationToken(String token, User user, Instant expiryAt) {
        this.token = requireNonEmpty(token, "token is required");
        this.user = requireNonNull(user, "user is required");
        this.expiryAt = requireNonNull(expiryAt, "expiry time is required");
    }

    public static VerificationToken create(User user) {
        return new VerificationToken(
                UUID.randomUUID().toString(),
                user,
                Instant.now().plus(Duration.ofHours(24))
        );
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiryAt);
    }
}
