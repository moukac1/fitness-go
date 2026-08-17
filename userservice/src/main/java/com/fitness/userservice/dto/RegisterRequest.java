package com.fitness.userservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class RegisterRequest {
    private String keycloakId;

    private String firstName;
    private String lastName;
    @Email(message = "invalid email format")
    @NotBlank(message = "email couldn't be empty")
    private String email;
    @NotBlank(message="password is required")
    @Size(min = 6, message = "password should have > 6 characters")
    private String password;

}
