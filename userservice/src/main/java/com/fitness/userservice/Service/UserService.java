package com.fitness.userservice.Service;


import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.mapper.UserMapper;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Builder
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new RuntimeException("User Not Found")
        );
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


    public  UserResponse register(@Valid RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            User existingUser = userRepository.findByEmail(request.getEmail()) ;
            UserResponse userResponse =new UserResponse();
            userResponse.setEmail(request.getEmail());
            userResponse.setKeycloakId(existingUser.getKeycloakId());
            userResponse.setPassword(request.getPassword());
            userResponse.setFirstName(request.getFirstName());
            userResponse.setLastName(request.getLastName());
            userResponse.setCreatedAt(existingUser.getCreatedAt());
            userResponse.setUpdatedAt(existingUser.getUpdatedAt());
            return userResponse;
        }

        User user=new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setKeycloakId(request.getKeycloakId());

        User userSaved = userRepository.save(user);
        return UserResponse.builder()
                .id(userSaved.getId())
                .keycloakId(userSaved.getKeycloakId())
                .email(userSaved.getEmail())
                .firstName(userSaved.getFirstName())
                .lastName(userSaved.getLastName())
                .createdAt(userSaved.getCreatedAt())
                .updatedAt(userSaved.getUpdatedAt())
                .build();

    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return  users
                .stream()
                .map( UserMapper::mapUserToUserResponse )
                .collect(Collectors.toList());
    }

    public  Boolean existsById(String userId) {
        return userRepository.existsByKeycloakId(userId);
    }
}
