package com.kage.controller;

import com.kage.dto.request.LoginRequest;
import com.kage.dto.request.RegisterUserRequest;
import com.kage.dto.response.RefreshTokenResponse;
import com.kage.dto.response.ApiResponse;
import com.kage.dto.response.LoginResponse;
import com.kage.dto.response.RegisterResponse;
import com.kage.exception.InvalidRefreshTokenException;
import com.kage.security.JwtService;
import com.kage.security.RefreshTokenService;
import com.kage.service.impl.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletResponse response) {

        LoginResponse loginResponse = authService.login(request);

        setRefreshTokenCookie(response, loginResponse.getRefreshToken());

        LoginResponse res = new LoginResponse();
        res.setName(loginResponse.getName());
        res.setEmail(loginResponse.getEmail());
        res.setAccessToken(loginResponse.getAccessToken());
        res.setUserRole(loginResponse.getUserRole());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Success", res));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @RequestBody RegisterUserRequest request) {

        RegisterResponse response = authService.register(request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Registered successfully", response));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(
            @RequestParam String token) {

        authService.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(
             HttpServletRequest request,
            HttpServletResponse response,
             @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String refreshToken = extractRefreshTokenFromCookie(request);

        //  Backward-compatible fallback
        if (refreshToken == null && authHeader != null && authHeader.startsWith("Bearer ")) {
            refreshToken = authHeader.substring(7);
        }

        RefreshTokenResponse tokenResponse =
                authService.refreshAccessToken(refreshToken);

        setRefreshTokenCookie(response, tokenResponse.getRefreshToken());

        RefreshTokenResponse res = new RefreshTokenResponse();
        res.setName(tokenResponse.getName());
        res.setEmail(tokenResponse.getEmail());
        res.setAccessToken(tokenResponse.getAccessToken());
        res.setUserRole(tokenResponse.getUserRole());

        return ResponseEntity.ok(new  ApiResponse<>(true, "Success", res));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request,
            HttpServletResponse response) {

        String refreshToken = extractRefreshTokenFromCookie(request);

        authService.logout(refreshToken);
        clearRefreshTokenCookie(response);

        return ResponseEntity.noContent().build();
    }

    // ================= HTTP HELPERS =================

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new InvalidRefreshTokenException("Refresh token missing");
        }

        for (Cookie cookie : request.getCookies()) {
            if ("refresh_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
 
        throw new InvalidRefreshTokenException("Refresh token missing");
    }

    private void setRefreshTokenCookie(
            HttpServletResponse response,
            String token) {

        Cookie cookie = new Cookie("refresh_token", token);
        cookie.setHttpOnly(true);

        // ❌ MUST be false for localhost HTTP
        cookie.setSecure(false);

        // ✅ allow all auth endpoints
        cookie.setPath("/");

        // 7 days
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);
    }

    private void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

