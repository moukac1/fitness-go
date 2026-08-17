package com.fitness.userservice.mapper;

import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import lombok.Builder;

@Builder
public class UserMapper {

    public static UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .keycloakId(user.getKeycloakId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static User mapUserResponseToUser(UserResponse userResponse) {
        User user = new User();
        user.setId(userResponse.getId());
        user.setKeycloakId(userResponse.getKeycloakId());
        user.setEmail(userResponse.getEmail());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setCreatedAt(userResponse.getCreatedAt());
        user.setUpdatedAt(userResponse.getUpdatedAt());
        return user;
    }
}
