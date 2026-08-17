package com.fitness.ai.service;


import com.fitness.ai.model.Recommendation;
import com.fitness.ai.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;


    public List<Recommendation> getUserRecommendations(String userId) {
        return recommendationRepository.findByUserId(userId) ;

    }
    public Recommendation getActivityRecommendations(String activityId) {
        return  recommendationRepository.findByActivityId(activityId).orElseThrow(()->new RuntimeException("Activity Not Found"));
    }
}
