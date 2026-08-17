package com.fitness.userservice.repository;


import com.fitness.userservice.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(@Email(message = "invalid email format") @NotBlank(message = "email couldn't be empty") String email);

    Boolean existsByKeycloakId(String userId);

    User findByEmail(@Email(message = "invalid email format") @NotBlank(message = "email couldn't be empty") String email);
}
