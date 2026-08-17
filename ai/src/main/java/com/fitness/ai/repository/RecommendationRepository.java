package com.fitness.ai.repository;

import com.fitness.ai.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {


    Optional<Recommendation> findByActivityId(String activityId);
    Recommendation save(Recommendation recommendation) ;

    List<Recommendation> findByUserId(String userId);
}
