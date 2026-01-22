package com.kage.repository;

import com.kage.entity.User;
import com.kage.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);


    Optional<VerificationToken> findByUser(User user);

    //void deleteByUser(String userEmail);
}