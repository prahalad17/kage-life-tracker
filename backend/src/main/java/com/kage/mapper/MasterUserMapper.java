package com.kage.mapper;

import com.kage.dto.request.CreateUserRequest;
import com.kage.dto.request.RegisterUserRequest;
import com.kage.dto.response.UserResponse;
import com.kage.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface  MasterUserMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "remarks", ignore = true)
    User toEntity(CreateUserRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "remarks", ignore = true)
    User toEntity(RegisterUserRequest request);


    UserResponse toResponse(User user);

    List<UserResponse> toResponse(List<User> users);

}
