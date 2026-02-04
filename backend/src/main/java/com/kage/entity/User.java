package com.kage.entity;

import com.kage.enums.UserRole;
import com.kage.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import static com.kage.util.DomainGuardsUtil.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    /* ---------- factory ---------- */

    public static User registerNew(String email, String encodedPassword ,String name) {
        User user = new User();
        user.email = requireNonEmpty(email , "email is required");
        user.name = requireNonEmpty(name , "name is required");
        user.password = encodedPassword;
        user.userStatus = UserStatus.PENDING_VERIFICATION;
        user.userRole = UserRole.ROLE_USER;
        return user;
    }

    /* ---------- behavior ---------- */

    public void activate() {
        this.userStatus = UserStatus.ACTIVE;
    }

    public void block() {
        this.userStatus = UserStatus.BLOCKED;
    }

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void deactivateAccount() {
        this.userStatus = UserStatus.INACTIVE;
        deactivate(); // from BaseEntity
    }

    public void activateAccount() {
        if (this.userStatus != UserStatus.PENDING_VERIFICATION) {
            throw new IllegalStateException("User not in verification state");
        }
        this.userStatus = UserStatus.ACTIVE;
    }
}

