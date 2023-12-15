package htlle.mailresponse.ChatGPT;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiKeyConfig {
    @JsonProperty("api_key")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
