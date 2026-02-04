package com.kage.service.impl;

import com.kage.dto.request.LoginRequest;
import com.kage.dto.request.RegisterUserRequest;
import com.kage.dto.response.RefreshTokenResponse;
import com.kage.dto.response.LoginResponse;
import com.kage.dto.response.RegisterResponse;
import com.kage.entity.RefreshToken;
import com.kage.entity.User;
import com.kage.entity.VerificationToken;
import com.kage.enums.RecordStatus;
import com.kage.enums.UserStatus;
import com.kage.exception.AuthenticationException;
import com.kage.exception.BadRequestException;
import com.kage.repository.UserRepository;
import com.kage.repository.VerificationTokenRepository;
import com.kage.security.CustomUserDetails;
import com.kage.security.GeneratedRefreshToken;
import com.kage.security.JwtService;
import com.kage.security.RefreshTokenService;
import com.kage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final RefreshTokenService refreshTokenService;


    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmailAndUserStatusAndStatus(
                        request.getEmail(),
                        UserStatus.ACTIVE,
                        RecordStatus.ACTIVE
                )
                .orElseThrow(() ->
                        new AuthenticationException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        String accessToken =
                jwtService.generateToken(new CustomUserDetails(user));

        //  Refresh token
        GeneratedRefreshToken refreshToken =
                refreshTokenService.issueNewToken(user);

        LoginResponse res = new LoginResponse();
        res.setAccessToken(accessToken);
        res.setUserRole(user.getUserRole());
        res.setName(user.getName());
        res.setRefreshToken(refreshToken.token());

        return res;
    }


    public RegisterResponse register(RegisterUserRequest request) {

        User user = userService.registerUser(request);
        sendVerification(user);

        return new RegisterResponse(
                "If the email exists and is not verified, a verification link has been sent"
        );

    }

    public void sendVerification(User user) {

        Optional<VerificationToken> existingTokenOpt =
                verificationTokenRepository.findByUser(user);

        if (existingTokenOpt.isPresent()) {
            VerificationToken existingToken = existingTokenOpt.get();

            //Rate Limiting
            if (existingToken.getCreatedAt()
                    .isAfter(Instant.now().minusSeconds(5))){
                throw new BadRequestException("Please wait before requesting again");
            }

            verificationTokenRepository.delete(existingToken);

        }

        VerificationToken verificationToken = VerificationToken.create(user);
        verificationTokenRepository.save(verificationToken);

        emailService.sendVerificationEmail(user.getEmail(),
                verificationToken.getToken());

    }


    @Transactional
    public void verifyEmail(String token) {

        VerificationToken verificationToken =
                verificationTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new BadRequestException("Invalid or expired token")
                        );

        // check expiry
        if (verificationToken.isExpired()) {
            verificationTokenRepository.delete(verificationToken);
            throw new BadRequestException("Invalid or expired token");
        }

        User user = verificationToken.getUser();

        if (user.getUserStatus() == UserStatus.ACTIVE) {
            verificationTokenRepository.delete(verificationToken);
            return;
        }

        if (user.getUserStatus() != UserStatus.PENDING_VERIFICATION) {
            throw new BadRequestException("Invalid verification state");
        }

        user.activateAccount();
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);

    }

    public RefreshTokenResponse refreshAccessToken(String refreshToken) {

        RefreshToken storedToken =
                refreshTokenService.validateRefreshToken(refreshToken);

        GeneratedRefreshToken rotatedRefreshToken =   refreshTokenService.rotateRefreshToken(storedToken);

        String newAccessToken =
                jwtService.generateToken(
                        new CustomUserDetails(storedToken.getUser())
                );


        RefreshTokenResponse res = new RefreshTokenResponse();

        res.setAccessToken(newAccessToken);
        res.setName(storedToken.getUser().getName());
        res.setRefreshToken(rotatedRefreshToken.token());
        res.setEmail(storedToken.getUser().getEmail());
        res.setUserRole(storedToken.getUser().getUserRole());

        return res;

    }

    public void logout(String refreshToken) {

        refreshTokenService.revokeRefreshToken(refreshToken);
    }

}