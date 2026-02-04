package com.kage.service.impl;

import com.kage.dto.request.RegisterUserRequest;
import com.kage.dto.request.UpdateUserRequest;
import com.kage.enums.RecordStatus;
import com.kage.enums.UserStatus;
import com.kage.dto.request.CreateUserRequest;
import com.kage.dto.response.UserResponse;
import com.kage.entity.User;
import com.kage.exception.BadRequestException;
import com.kage.mapper.UserMapper;
import com.kage.repository.UserRepository;
import com.kage.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder  passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmailAndStatus(request.getEmail(), RecordStatus.ACTIVE)) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.registerNew(
                request.getEmail(),
                request.getName(),
                passwordEncoder.encode(request.getPassword())
        );
        user.activate();
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public User registerUser(RegisterUserRequest request) {

        return userRepository.findByEmail(request.getEmail())
                .map(existing -> handleReRegistration(existing, request))
                .orElseGet(() -> createFreshRegistration(request));
    }

    private User handleReRegistration(User user, RegisterUserRequest request) {

        switch (user.getUserStatus()) {

            case ACTIVE ->
                    throw new BadRequestException("Email already registered");

            case PENDING_VERIFICATION -> {
                user.changePassword(
                        passwordEncoder.encode(request.getPassword())
                );
                return user; // dirty checking
            }

            case BLOCKED ->
                    throw new BadRequestException("Registration not allowed");

            default ->
                    throw new IllegalStateException(
                            "Unsupported user status: " + user.getUserStatus()
                    );
        }
    }

    private User createFreshRegistration(RegisterUserRequest request) {

        User user = User.registerNew(
                request.getEmail(),
                request.getName(),
                passwordEncoder.encode(request.getPassword())
        );

        userRepository.save(user);
        return user;
    }



    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {

        User user = loadActiveUser(id);
        return userMapper.toResponse(user);
    }

    @Override
    public void softDeleteUser(Long id) {
        User user = loadActiveUser(id);
        user.deactivateAccount();
    }

    @Override
    public List<UserResponse> getAllUser() {

        return userRepository.findByUserStatusAndStatus(
                        UserStatus.ACTIVE, RecordStatus.ACTIVE)
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request) {

        User user = userRepository
                .findByEmailAndUserStatusAndStatus(
                        request.getEmail(),
                        UserStatus.ACTIVE,
                        RecordStatus.ACTIVE
                )
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
        userMapper.updateEntityFromDto(request, user);
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User loadActiveUser(Long userId) {
        return userRepository
                .findByIdAndUserStatusAndStatus(
                        userId,
                        UserStatus.ACTIVE,
                        RecordStatus.ACTIVE
                )
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
