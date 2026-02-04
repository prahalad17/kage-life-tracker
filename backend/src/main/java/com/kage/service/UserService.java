package com.kage.service;

import com.kage.dto.request.CreateUserRequest;
import com.kage.dto.request.RegisterUserRequest;
import com.kage.dto.request.UpdateUserRequest;
import com.kage.dto.response.UserResponse;
import com.kage.entity.User;
import jakarta.validation.Valid;

import java.util.List;


public interface UserService {

        UserResponse createUser(CreateUserRequest request);

    User registerUser(RegisterUserRequest request);

    UserResponse getUserById(Long id);

    void softDeleteUser(Long id);

    List<UserResponse> getAllUser();

    UserResponse updateUser(@Valid UpdateUserRequest request);

    User loadActiveUser(Long userId);
}

