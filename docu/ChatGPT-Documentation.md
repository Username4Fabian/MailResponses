# ChatGPT-Dokumentation


- Zuerst muss in conig/config.json der API-key eingetragen werden.
```json
{
"api_key": "Your API-KEY"
}
```

- Um die API zu verwenden kann die **chatGPT** Methode verwendet werden.
- Als erstes Argument kann die Stimmung der Nachricht übergeben werden.
- Als zweites Argument kann das eigentliche Email übergeben werden.
```java
    chatGPT("Antworte auf diese Email in einem aggresiven beleidigenden Stil", input);
```


- Hier wird der API-Key eingelesen und zur späteren Verwendung gespeichert.
```java
public static String loadApiKey() {
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile = new File("config/config.json");

        try {
            ApiKeyConfig apiKeyConfig = objectMapper.readValue(configFile, ApiKeyConfig.class);
            String apiKey = apiKeyConfig.getApiKey();
            return apiKey;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
```

- Hier findet der API Call zur OpenAI API statt.
```java
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
```

- Dieser wird dann hier weiterverarbeitet
```java
public static String chatGPT(String responseMood, String text) throws Exception {

        JSONObject data = getJsonObject(responseMood, text);

        HttpURLConnection con = getHttpURLConnection(data);

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines().reduce((a, b) -> a + b).get();

        return new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");
    }
```

- Sehr wichtig sind auch die Einstellungen die im JSON String getroffen werden müssen
```java
private static JSONObject getJsonObject(String responseMood, String text) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("model", "text-davinci-003");
        data.put("prompt", responseMood + ": " + text);
        data.put("max_tokens", 4000);
        data.put("temperature", 1.0);
        return data;
    }
```