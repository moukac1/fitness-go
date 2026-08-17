package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.mapper.ActivityMapper;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor

public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService ;
    // Only for RabbitMQ
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.name}")
    private String exchange ;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    //
    public ActivityResponse trackActivity(ActivityRequest request) {

        //let's check if it is existing or nnot
        boolean isValidated = userValidationService.isUserValidated(request.getUserId()) ;
        if (!isValidated) {
            throw new RuntimeException("User is not validated");
        }
        Activity activity = Activity.builder()

                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        // publish to rabbitMq for AI processing
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
        }catch(Exception e) {
            log.error("failed to send activity", e);
        }

        return ActivityMapper.mapToActivityResponse(savedActivity);
    }

    public List<ActivityResponse> getUserActivities(String userId) {

        List<Activity> activities = activityRepository.findByUserId((userId)) ;

        return activities.stream()
                .map(ActivityMapper::mapToActivityResponse)
                .collect(Collectors.toList());
    }

    public  ActivityResponse getActivityById(String activityId) {
        Activity activity =  activityRepository.findById(activityId)
                .orElseThrow(
                        () -> new RuntimeException("Activity not found")
                );
        return ActivityMapper
                .mapToActivityResponse(activity);
    }
}
