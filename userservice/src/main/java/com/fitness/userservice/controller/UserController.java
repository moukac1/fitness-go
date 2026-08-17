package com.fitness.userservice.controller;


import com.fitness.userservice.Service.UserService;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }
    @GetMapping("/{userId}/validated")
    public ResponseEntity<Boolean> existsById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.existsById(userId));
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers())  ;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register( @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request)) ;
    }
}
