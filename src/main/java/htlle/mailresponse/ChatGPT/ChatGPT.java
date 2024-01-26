package htlle.mailresponse.ChatGPT;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class ChatGPT {
    private static final String API_Key = loadApiKey();

    public static String getAIResponseFromOpenAI(String mood, String userMessage) {
        if(userMessage.toLowerCase().contains("penis")) {
            return "Böses Wort";
        }

        String openAIEndpoint = "https://api.openai.com/v1/chat/completions";
        String model = "gpt-3.5-turbo";
        int maxTokens = 100;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_Key);
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessageMap = new HashMap<>();
        userMessageMap.put("role", "user");
        userMessageMap.put("content", mood + ": " + userMessage);
        messages.add(userMessageMap);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", maxTokens);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

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