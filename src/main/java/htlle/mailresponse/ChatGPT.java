package htlle.mailresponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatGPT {

    private static final String API_Key = loadApiKey();
    private static final String input = "Sehr geehrter Herr Kondert! Können Sie sich mit Jogurt einschmieren? Mfg. Klaus Kepplinger";

    public static void main(String[] args) throws Exception {
        chatGPT("Antworte auf diese Email in einem aggresiven beleidigenden Stil", input);
    }

    public static void chatGPT(String responseMood, String text) throws Exception {

        JSONObject data = getJsonObject(responseMood, text);

        HttpURLConnection con = getHttpURLConnection(data);

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines().reduce((a, b) -> a + b).get();

        System.out.println(new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text"));
    }

    private static HttpURLConnection getHttpURLConnection(JSONObject data) throws IOException {
        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + API_Key);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());
        return con;
    }

    private static JSONObject getJsonObject(String responseMood, String text) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("model", "text-davinci-003");
        data.put("prompt", responseMood + ": " + text);
        data.put("max_tokens", 4000);
        data.put("temperature", 1.0);
        return data;
    }

    public static String loadApiKey() {
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile = new File("config/config.json"); // Replace with your JSON file path

        try {
            ApiKeyConfig apiKeyConfig = objectMapper.readValue(configFile, ApiKeyConfig.class);
            String apiKey = apiKeyConfig.getApiKey();
            return apiKey;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
