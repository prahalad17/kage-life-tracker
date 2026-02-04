package com.kage.repository;

import com.kage.entity.User;
import com.kage.enums.RecordStatus;
import com.kage.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndUserStatusAndStatus(@Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email, UserStatus userStatus, RecordStatus recordStatus);

    Optional<User> findByIdAndUserStatusAndStatus(Long id, UserStatus userStatus, RecordStatus recordStatus);

    List<User> findByUserStatusAndStatus(UserStatus userStatus, RecordStatus recordStatus);

    Optional<User> findByIdAndStatus(Long userId, RecordStatus recordStatus);

    boolean existsByEmailAndStatus(@NotBlank(message = "Email is required") String email, RecordStatus recordStatus);
}