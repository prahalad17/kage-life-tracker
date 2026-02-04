package com.kage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import static com.kage.util.DomainGuardsUtil.*;

@Entity
@Table(
        name = "refresh_tokens",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"token_hash"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {

    @Column(name = "token_hash", nullable = false, unique = true, length = 255)
    private String tokenHash;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean revoked;

    protected RefreshToken(String tokenHash, User user, Instant expiresAt) {
        this.tokenHash = requireNonEmpty(tokenHash, "token hash is required");
        this.user = requireNonNull(user, "user is required");
        this.expiresAt = requireNonNull(expiresAt, "expiry time is required");
        this.revoked = false;
    }

    public static RefreshToken issue(User user, String tokenHash, Instant expiresAt) {
        return new RefreshToken(tokenHash,user,  expiresAt);
    }

    /* -------- Domain behavior -------- */

    public boolean isExpired() {
        return expiresAt.isBefore(Instant.now());
    }
    public void revoke() {
        this.revoked = true;
    }

    public boolean isValid() {
        return !revoked && !isExpired();
    }


}
