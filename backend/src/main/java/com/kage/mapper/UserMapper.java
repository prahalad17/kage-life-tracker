package com.kage.mapper;

import com.kage.dto.request.CreateUserRequest;
import com.kage.dto.request.RegisterUserRequest;
import com.kage.dto.request.UpdateUserRequest;
import com.kage.dto.response.UserResponse;
import com.kage.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "remarks", ignore = true)
//    User toEntity(CreateUserRequest request);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "remarks", ignore = true)
//    User toEntity(RegisterUserRequest request);


    UserResponse toResponse(User user);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "remarks", ignore = true)
//    User toEntity(UpdateUserRequest request);

    void updateEntityFromDto(UpdateUserRequest dto , @MappingTarget User user);
}
