package htlle.mailresponse.ChatGPT;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * This class is responsible for interacting with the OpenAI API to generate AI responses.
 */
public class ChatGPT {
    private static final String API_KEY = loadApiKey();

    /**
     * This method sends a request to the OpenAI API and returns the AI-generated response.
     *
     * @param mood        The mood of the user message.
     * @param userMessage The user's message.
     * @return The AI-generated response.
     */
    public static String getAIResponseFromOpenAI(String mood, String userMessage) {
        // Check for inappropriate content
        if (userMessage.toLowerCase().contains("penis")) {
            return "Inappropriate content";
        }

        String openAIEndpoint = "https://api.openai.com/v1/chat/completions";
        String model = "gpt-3.5-turbo";
        int maxTokens = 150;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare the request body
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> systemMessageMap = new HashMap<>();
        systemMessageMap.put("role", "system");
        systemMessageMap.put("content", "You are a helpful E-Mail assistant. The message sent to you is a E-Mail, the user needs an answer to. Do not referer to the user themselves, just generate a proper response E-Mail. Make sure to match the general tone of the input message.");
        messages.add(systemMessageMap);

        Map<String, String> userMessageMap = new HashMap<>();
        userMessageMap.put("role", "user");
        userMessageMap.put("content", mood + ": " + userMessage);
        messages.add(userMessageMap);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", maxTokens);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send the request and handle the response
        ResponseEntity<Map> response = restTemplate.postForEntity(openAIEndpoint, requestEntity, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> choice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                return (String) message.get("content");
            } else {
                return "Empty response from OpenAI";
            }
        } else {
            String errorMessage = "Error from OpenAI: ";
            if (response.getBody() != null) {
                errorMessage += response.getBody().toString();
            } else {
                errorMessage += response.getStatusCode();
            }
            throw new RuntimeException(errorMessage);
        }
    }

    /**
     * This method loads the OpenAI API key from the application properties file.
     *
     * @return The OpenAI API key.
     */
    public static String loadApiKey() {
        Properties prop = new Properties();
        InputStream input;
        try {
            input = new FileInputStream("src/main/resources/application.properties");
            prop.load(input);
            return prop.getProperty("openai.api.key");
        } catch (IOException ignored) {
        }
        return null;
    }
}