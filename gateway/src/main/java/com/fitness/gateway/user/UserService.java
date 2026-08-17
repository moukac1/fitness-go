package com.fitness.gateway.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final WebClient userServiceWebClient;

    public Mono<Boolean> isUserValidated(String userId) {
        return userServiceWebClient.get()
                .uri("/api/users/{userId}/validated", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                               return Mono.error( new RuntimeException("User not found")).hasElement();
                            }
                            else if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                                return Mono.error(new RuntimeException("Internal server error")).hasElement();
                            }
                            return Mono.just(false);
        });

    }

    public Mono<UserResponse> registerUser(RegisterRequest registerRequest) {
        log.info("calling user registration api for email {}" , registerRequest.getEmail()  );
        return userServiceWebClient.post()
                .uri("/api/users/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                        return Mono.error(new RuntimeException("Internal server error"));
                    }
                    else if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                        return Mono.error(new RuntimeException("Internal server error"));
                    }
                    return Mono.error(new RuntimeException("Internal server error"));
                });
    }
}
