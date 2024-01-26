package htlle.mailresponse.ChatGPT;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class ChatGPT {
    private static final String API_Key = loadApiKey();

    public static String chatGPT(String responseMood, String text) throws Exception {

        JSONObject data = getJsonObject(responseMood, text);
        HttpURLConnection con = getHttpURLConnection(data);
        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines().reduce((a, b) -> a + b).get();
        String response = new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text").trim();
        System.out.println(response);

        return new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");
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
        data.put("model", "gpt-3.5-turbo");
        data.put("prompt", responseMood + ": " + text);
        data.put("max_tokens", 4000);
        data.put("temperature", 1.0);
        return data;
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