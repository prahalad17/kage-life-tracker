package com.kage.controller;

import com.kage.dto.request.LoginRequest;
import com.kage.dto.request.RegisterUserRequest;
import com.kage.dto.response.ApiResponse;
import com.kage.dto.response.LoginResponse;
import com.kage.dto.response.RegisterResponse;
import com.kage.service.impl.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Success", response));
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
}

