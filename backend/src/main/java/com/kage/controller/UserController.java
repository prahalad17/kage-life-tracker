package com.kage.controller;

import com.kage.dto.request.UpdateUserRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kage.dto.request.CreateUserRequest;
import com.kage.dto.response.ApiResponse;
import com.kage.dto.response.UserResponse;
import com.kage.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        UserResponse response = service.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true,
                        "User created successfully",
                        response));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @Valid @RequestBody UpdateUserRequest request) {

        UserResponse response = service.updateUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true,
                        "User created successfully",
                        response));
    }


    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUser() {

        List<UserResponse> response = service.getAllUser();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Success", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(
            @PathVariable Long id) {

        UserResponse response = service.getUserById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Success", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id) {

        service.softDeleteUser(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true,
                        "User deleted successfully",
                        null));
    }
}
