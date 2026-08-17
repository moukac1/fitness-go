package com.fitness.ai.model;

import com.fitness.activityservice.model.ActivityType;
import jakarta.persistence.Id;

import lombok.Data;


import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity {
    @Id
    private String id;
    private String userId;
    private ActivityType type  ;

    private Integer duration;
    private Integer caloriesBurned ;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMetrics ;
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt ;


}
