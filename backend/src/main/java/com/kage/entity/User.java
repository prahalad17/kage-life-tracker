package com.kage.entity;

import com.kage.enums.UserRole;
import com.kage.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "master_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column()
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

}
