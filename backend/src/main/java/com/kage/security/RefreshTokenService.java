package com.kage.security;

import com.kage.entity.RefreshToken;
import com.kage.entity.User;
import com.kage.exception.InvalidRefreshTokenException;
import com.kage.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor()
public class RefreshTokenService {

    private static final int TOKEN_BYTE_LENGTH = 64; // 512 bits
    private static final long REFRESH_TOKEN_DAYS = 7;

    private final RefreshTokenRepository refreshTokenRepository;


    public RefreshToken validateRefreshToken(String rawToken) {
        String tokenHash = hashToken(rawToken);

        RefreshToken refreshToken =  refreshTokenRepository.findByTokenHash(tokenHash).orElseThrow(() -> new IllegalStateException("Invalid refresh token"));

        if(refreshToken.getExpiresAt().isBefore(Instant.now())){
            throw new IllegalStateException("Refresh token expired");
        }

        return refreshToken;

    }


    public GeneratedRefreshToken rotateRefreshToken(RefreshToken oldToken) {

        // revoke old token
        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);

        // issue new token
        return createRefreshToken(oldToken.getUser());
    }

    public GeneratedRefreshToken createRefreshToken(User user) {

        String rawToken = generateSecureToken();
        String hashedToken = hashToken(rawToken);
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .tokenHash(hashedToken)
                .expiresAt(Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS))
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);

        // RAW token is returned ONCE (never stored)
        return new GeneratedRefreshToken(rawToken, refreshToken.getExpiresAt());


    }

    // ================= UTIL =================
    private String generateSecureToken(){

        byte[] randomBytes = new byte[TOKEN_BYTE_LENGTH];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private String hashToken(String token){

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes  = digest.digest(token.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hashedBytes);

        }catch (NoSuchAlgorithmException e){
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    public void revokeRefreshToken(String rawToken) {

        String tokenHash = hashToken(rawToken);

        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenHash(tokenHash)
                .orElseThrow(() ->
                        new InvalidRefreshTokenException("Invalid refresh token"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    // ================= REVOKE ALL TOKENS (OPTIONAL) =================

    public void revokeAllUserTokens(Long userId) {

        List<RefreshToken> tokens =
                refreshTokenRepository.findAllByUserId(userId);

        for (RefreshToken token : tokens) {
            token.setRevoked(true);
        }

        refreshTokenRepository.saveAll(tokens);
    }

}
