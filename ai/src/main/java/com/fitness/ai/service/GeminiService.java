package com.fitness.ai.service;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

//commentaire
@Service
@Slf4j
@Data
//@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.api.url}")
    private String GEMINI_URL;
    @Value("${gemini.api.key}")
    private String GEMINI_API_KEY;

    private final WebClient webClient;

    public GeminiService() {
        this.webClient = WebClient.create();
    }
    public String getAnswer(String question ){
        Map<String,Object> requestBody  = Map.of(
                "contents" , new Object[]{
                        Map.of(
                                "parts" , new Object[]{
                                        Map.of(
                                                "text" , question
                                        )
                                }
                        )
                }

        );

        return webClient.post()
                .uri(GEMINI_URL + GEMINI_API_KEY)
                .header("Content-Type" , "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
}
}
