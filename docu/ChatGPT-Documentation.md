## Dokumentation der Klasse `ChatGPT`

Die Klasse `ChatGPT` befindet sich im Paket `htlle.mailresponse.ChatGPT` und ist für die Interaktion mit der OpenAI API konzipiert. Ihr Hauptzweck ist die Generierung von KI-basierten Antworten auf Benutzernachrichten, insbesondere im Kontext von E-Mail-Antwortsystemen.

### Hauptmerkmale

- **Interaktion mit OpenAI API**: Sendet Benutzernachrichten an die OpenAI API und empfängt KI-generierte Antworten.
- **Inhaltsfilterung**: Prüft die Benutzernachricht auf unangemessene Inhalte, bevor sie an die API gesendet wird.
- **API-Schlüssel Management**: Lädt den für die Authentifizierung bei der OpenAI API benötigten Schlüssel dynamisch aus einer Konfigurationsdatei.

### Methodenübersicht

#### `public static String getAIResponseFromOpenAI(String mood, String userMessage)`

Diese Methode ist das Herzstück der Klasse und verantwortlich für den gesamten Prozess der Anfrageerstellung, Sendung und Antwortverarbeitung von der OpenAI API.

- **Parameter**:
  - `mood`: Die Stimmung der Benutzernachricht, die den Ton der Antwort beeinflussen kann.
  - `userMessage`: Die eigentliche Nachricht des Benutzers, die verarbeitet werden soll.

- **Rückgabewert**: Die von der OpenAI API generierte Antwort als `String`.

- **Funktionsweise**:
  1. Prüft die Nachricht auf unangemessene Inhalte.
  2. Bereitet die HTTP-Anfrage vor, einschließlich der Authentifizierung und des Anfragekörpers.
  3. Verarbeitet die Antwort von OpenAI und extrahiert den Antworttext.

- **Beispielcode**:

  ```java
  HttpHeaders headers = new HttpHeaders();
  headers.set("Authorization", "Bearer " + API_KEY);
  headers.setContentType(MediaType.APPLICATION_JSON);

  Map<String, Object> requestBody = new HashMap<>();
  requestBody.put("model", "gpt-3.5-turbo");
  requestBody.put("messages", messages);
  requestBody.put("max_tokens", 150);

  HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
  ResponseEntity<Map> response = restTemplate.postForEntity(openAIEndpoint, requestEntity, Map.class);
  ```

#### `private static String loadApiKey()`

Lädt den API-Schlüssel aus der Konfigurationsdatei `application.properties`.

- **Rückgabewert**: Der geladene API-Schlüssel als `String`.

- **Funktionsweise**:
  1. Öffnet die Konfigurationsdatei.
  2. Liest den Wert des API-Schlüssels aus.
  3. Gibt den Schlüssel zurück oder `null` bei Fehlern.

- **Beispielcode**:

  ```java
  Properties prop = new Properties();
  try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
      prop.load(input);
      return prop.getProperty("openai.api.key");
  } catch (IOException ignored) {
      return null;
  }
  ```

### Einsatzszenarien

Die Klasse `ChatGPT` eignet sich für Projekte, die eine Integration mit der OpenAI API benötigen, um automatisierte Antworten auf Benutzernachrichten zu generieren. Dies kann in verschiedenen Kontexten nützlich sein, z.B. in Kundensupport-Systemen, E-Mail-Automatisierungsdiensten oder Chatbots.

### Sicherheitshinweise

Bei der Verwendung dieser Klasse ist zu beachten, dass der API-Schlüssel sicher gespeichert und verwaltet werden muss, um Missbrauch zu vermeiden. Zudem sollte die Inhaltsfilterung an die spezifischen Anforderungen des Einsatzgebietes angepasst werden.

### Zusammenfassung

Die `ChatGPT`-Klasse bietet eine einfache und effektive Möglichkeit, die OpenAI API in Java-basierten Anwendungen zu nutzen. Durch das dynamische Laden von API-Schlüsseln und die vordefinierte Inhaltsfilterung erleichtert sie die Entwicklung von Anwendungen, die intelligente, KI-gestützte Antworten generieren müssen.