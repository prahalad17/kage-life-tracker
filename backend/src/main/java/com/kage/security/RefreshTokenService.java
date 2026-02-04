package com.kage.security;

import com.kage.entity.RefreshToken;
import com.kage.entity.User;
import com.kage.exception.InvalidRefreshTokenException;
import com.kage.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private static final int TOKEN_BYTE_LENGTH = 64; // 512 bits
    private static final long REFRESH_TOKEN_DAYS = 7;

    private final RefreshTokenRepository refreshTokenRepository;


    public RefreshToken validateRefreshToken(String rawToken) {

        String tokenHash = hashToken(rawToken);

        RefreshToken refreshToken =  refreshTokenRepository.findByTokenHash(tokenHash).orElseThrow(() -> new IllegalStateException("Invalid refresh token"));

        if(!refreshToken.isValid()){
            throw new IllegalStateException("Refresh token expired");
        }

        return refreshToken;

    }


    public GeneratedRefreshToken rotateRefreshToken(RefreshToken oldToken) {

        // revoke old token
        oldToken.revoke();
        refreshTokenRepository.save(oldToken);

        // issue new token
        return issueNewToken(oldToken.getUser());
    }

    public GeneratedRefreshToken issueNewToken(User user) {

        String rawToken = generateSecureToken();
        String hashedToken = hashToken(rawToken);

        RefreshToken refreshToken = RefreshToken.issue(
                user,
                hashedToken,
                Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS)
        );

        refreshTokenRepository.save(refreshToken);

        return new GeneratedRefreshToken(
                rawToken,
                refreshToken.getExpiresAt()
        );
    }



    // ================= REVOKE  TOKENS =================

    public void revokeRefreshToken(String rawToken) {

        String tokenHash = hashToken(rawToken);

        RefreshToken token = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() ->
                        new InvalidRefreshTokenException("Invalid refresh token"));

        token.revoke(); // no save()
    }

    public void revokeAllUserTokens(Long userId) {

        List<RefreshToken> tokens =
                refreshTokenRepository.findAllByUserId(userId);

        tokens.forEach(RefreshToken::revoke);
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


}
