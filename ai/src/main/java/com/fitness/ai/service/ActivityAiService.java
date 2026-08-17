package com.fitness.ai.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.ai.model.Activity;
import com.fitness.ai.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityAiService {


    private final GeminiService geminiService;
    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createAPrompt(activity) ;
        String aiResponse = geminiService.getAnswer(prompt);
        log.info(aiResponse);
        processAiResponse(activity , aiResponse)  ; 
        return processAiResponse(activity , aiResponse);
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(aiResponse);
            JsonNode activityNode = jsonNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");
            // till the point the text is ssaved in plain text in jsonString
            String jsonString = activityNode.asText();
            String cleanJsonString = jsonString
                    .replaceAll("```json\\n" , "")
                    .replaceAll("\\n```", "")
                    .trim();
            log.info(cleanJsonString);
            JsonNode analysisJson = objectMapper.readTree(cleanJsonString);

            // we will store our analysis here
            String analysisString = analysisJson.path("analysis")
                    .path("overall")
                    .asText();

            // we will store a list one element which is recommendatioon
            List<String> improvements = Collections.singletonList(analysisJson
                    .path("improvements")
                    .path("recommendation")
            .asText());
            List<String> suggestions = Collections.singletonList(analysisJson
                    .path("suggestions")
                    .path("description")
                    .asText());
            List<String> safetySuggestions = Collections.singletonList(analysisJson
                    .path("suggestions")
                    .path("description")
                    .asText());

            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .recommendation(analysisString)
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safetySuggestions(safetySuggestions)
                    .createdAt(activity.getCreatedAt())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String createAPrompt(Activity activity) {
        return String.format("""
                Analyse this fitness activity, and give me the anlysis like this fromat : 
                {
                    "analysis" : {
                        "caloriesBurned" : "caloriesBurned here ",
                      
                        "overall" : "givee an overall" ,
                        
                    }
                    "improvements" : {
                        "recommendation" : "detailed recommendation"
                    }
                    "suggestions" : {
                        "workout" : "workout here" , 
                        "description" : "detailed description"
                    }
                    
                                                          
                }
                
                analyse this activity : 
                ActivityType : %s , 
                caloriesBurned : %d ,
                
                
                Duration : %d    
                
                Provide detailed analysis focusing on performance, improvements ,and suggestions.
                Ensure the response follows the EXACT JSON format shown above.            
                """ ,
                activity.getType() , activity.getCaloriesBurned() , activity.getDuration()) ;
    }


}
