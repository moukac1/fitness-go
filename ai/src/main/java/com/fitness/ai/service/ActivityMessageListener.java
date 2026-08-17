package com.fitness.ai.service;


import com.fitness.ai.model.Activity;
import com.fitness.ai.model.Recommendation;
import com.fitness.ai.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService activityAiService;
    private final RecommendationRepository recommendationRepository;
    @RabbitListener(queues = "activity.queue" )
    public void processActivity(Activity activity) {
        log.info("Received Message: {}", activity);
        try {
            activityAiService.generateRecommendation(activity);
            Thread.sleep(4000);

        } catch (Exception e) {
            log.error("Failed to process activity {}: {}", activity.getId(), e.getMessage());
            // Don't rethrow - this prevents the message from requeuing infinitely
        }
        Recommendation recommendation = activityAiService.generateRecommendation(activity) ;
        recommendationRepository.save(recommendation);
    }



}
