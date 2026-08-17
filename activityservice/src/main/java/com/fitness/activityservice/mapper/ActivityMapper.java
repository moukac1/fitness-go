package com.fitness.activityservice.mapper;

import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActivityMapper {

    public static Activity mapToActivity(ActivityResponse activityResponse){
        return Activity.builder()
                .userId(activityResponse.getUserId())
                .type(activityResponse.getType())
                .duration(activityResponse.getDuration())
                .caloriesBurned(activityResponse.getCaloriesBurned())
                .startTime(activityResponse.getStartTime())
                .additionalMetrics(activityResponse.getAdditionalMetrics())
                .build();
    }
    public static ActivityResponse mapToActivityResponse(Activity activity){
        return ActivityResponse.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType())
                .duration(activity.getDuration())
                .caloriesBurned(activity.getCaloriesBurned())
                .startTime(activity.getStartTime())
                .additionalMetrics(activity.getAdditionalMetrics())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
    }
}
