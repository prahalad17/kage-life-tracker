package com.kage.service.impl;

import com.kage.dto.request.RegisterUserRequest;
import com.kage.enums.RecordStatus;
import com.kage.enums.UserRole;
import com.kage.enums.UserStatus;
import com.kage.dto.request.CreateUserRequest;
import com.kage.dto.response.UserResponse;
import com.kage.entity.User;
import com.kage.exception.BadRequestException;
import com.kage.exception.ResourceNotFoundException;
import com.kage.mapper.MasterUserMapper;
import com.kage.repository.UserRepository;
import com.kage.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MasterUserMapper userMapper;
    private final PasswordEncoder  passwordEncoder;

   /* public UserServiceImpl(
            UserRepository repository,
            MasterUserMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.userMapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }*/

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User saved = userRepository.save(user);

        return userMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public User registerUser(RegisterUserRequest request) {

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {

            User user = userOpt.get();

            switch (user.getUserStatus()) {

                case ACTIVE:
                    throw new BadRequestException("Email already registered");

                case PENDING_VERIFICATION:
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    userRepository.save(user);
                    return user;

                case BLOCKED:
                    throw new BadRequestException("Registration not allowed");

                default:
                    throw new IllegalStateException(
                            "Unsupported user status: " + user.getStatus()
                    );
            }
        }

        // Fresh registration
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserStatus(UserStatus.PENDING_VERIFICATION);
        user.setStatus(RecordStatus.ACTIVE);
        user.setUserRole(UserRole.USER);

        return userRepository.save(user);
    }



    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .filter(u -> u.getStatus() == RecordStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    @Override
    public void softDeleteUser(Long id, String remarks) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.softDelete(remarks);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllUser() {

        return userMapper.toResponse(userRepository.findAll());

        /*List<User> users = userRepository.findAll();

        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setEmail(user.getEmail());
            userResponse.setId(user.getId());
            userResponseList.add(userResponse);
        }
        return userResponseList;*/
    }
}
