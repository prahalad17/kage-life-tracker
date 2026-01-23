package com.kage.security;

import com.kage.entity.User;
import com.kage.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;


public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }



    // IDENTIFIER (email acts as username)
    @Override
    public String getUsername() {

        return user.getEmail();
    }

    // 🔐 PASSWORD (already hashed)
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(user.getUserRole().name()));

        return authorities;

    }

    // ACCOUNT STATUS CHECKS
    @Override
    public boolean isAccountNonExpired() {
        return true; // optional, can extend later
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getUserStatus() != UserStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getUserStatus() == UserStatus.ACTIVE;
    }
    // Optional helper
    public User getUser() {
        return user;
    }

}
