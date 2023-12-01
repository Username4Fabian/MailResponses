# MailResponses

## Summary:
The project, named `MailResponses`, is a Java-based application built using Spring Boot and Maven. It is designed to interact with the OpenAI GPT-3 API and handle email responses. The application is divided into two main parts: the backend, which is written in Java, and the frontend, which is written in HTML, CSS, and JavaScript.

Backend:
- `ApiKeyConfig.java`: This class maps the JSON configuration file that contains the API key needed to interact with the OpenAI GPT-3 API.
- `ChatGPT.java`: This class interacts with the OpenAI GPT-3 API to generate responses to given prompts.
- `Email.java`: This class is a JPA entity that represents an email. It includes fields for the email's id, receiver, sender, subject, content, and timestamp.
- `EmailDatabase.java`: This class interacts with a H2 database to store and retrieve `Email` objects.
- `EmailRepository.java`: This interface extends `JpaRepository` to gain access to a wide range of methods for interacting with the database.
- `EmailUtil.java`: This utility class uses the JavaMail API to send an email.
- `MailresponseApplication.java`: This class is the entry point of the Spring Boot application.
- `ReceiveMail.java`: This class receives emails from an Outlook account using the JavaMail API.
- `SendMail.java`: This class sends emails from an Outlook account using the JavaMail API.

Frontend:
- `index.html`: This HTML document provides a user interface for the email client application.
- `script.js`: This JavaScript file simulates the addition of new emails to the email client interface and handles user interactions.
- `style.css`: This CSS file is used for styling the web page.

Server Configuration/Database:
- `application.properties`: This properties file configures the H2 database connection, Hibernate settings, and other application-wide settings.
- `MailResponse.mv.db`: This is the database file for the H2 database used in the application. It stores all the data of the application, including tables, records, and other database objects.

The application is designed to receive emails from an Outlook account, generate responses using the OpenAI GPT-3 API, and send the responses back. It also stores the emails in a H2 database. The frontend provides a user interface for displaying the emails and interacting with the application.

<br>
<br>

## Documentation - Backend - 01.12.2023:
### ApiKeyConfig.java:
The code snippet provided is a simple Java class named `ApiKeyConfig` that is part of the `htlle.mailresponse` package. This class is used to map the JSON configuration file that contains the API key needed to interact with the OpenAI GPT-3 API.

The `ApiKeyConfig` class has a single private field, `apiKey`, which is annotated with `@JsonProperty("api_key")`. This annotation is from the Jackson library and it indicates that the `apiKey` field should be mapped to the `api_key` property in the JSON configuration file.

```java
@JsonProperty("api_key")
private String apiKey;
```

The class also has a public getter method, `getApiKey()`, which returns the value of the `apiKey` field. This method is used to retrieve the API key when needed.

```java
public String getApiKey() {
    return apiKey;
}
```

In summary, the `ApiKeyConfig` class is a simple data holder for the API key needed to interact with the OpenAI GPT-3 API. It uses the Jackson library to map the API key from a JSON configuration file to a Java object.

<br>

### ChatGPT.java:
The provided code is a Java program that interacts with the OpenAI GPT-3 API to generate responses to given prompts. The main class is `ChatGPT`, and it uses the `java.net.HttpURLConnection` class to make HTTP requests to the API.

The `main` method calls the `chatGPT` method with two arguments: a response mood and a text. The response mood is a string that describes the tone of the response, and the text is the actual content to be responded to.

```java
public static void main(String[] args) throws Exception {
    chatGPT("Antworte auf diese Email in einem aggresiven beleidigenden Stil", input);
}
```

The `chatGPT` method constructs a JSON object with the necessary parameters for the API request, makes the request, and processes the response. The `getJsonObject` method is used to create the JSON object. It takes the response mood and text as arguments, and returns a JSON object with the model, prompt, max tokens, and temperature parameters set.

```java
JSONObject data = getJsonObject(responseMood, text);
```

The `getHttpURLConnection` method is used to set up the HTTP connection. It takes the JSON object as an argument, sets the necessary request properties, and returns the connection object.

```java
HttpURLConnection con = getHttpURLConnection(data);
```

The response from the API is read, processed, and printed to the console. It is also saved to a database using the `EmailDatabase` class.

```java
String response = new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");
```

The `loadApiKey` method is used to load the API key from a JSON configuration file. It uses the `com.fasterxml.jackson.databind.ObjectMapper` class to read the file and return the API key.

```java
private static final String API_Key = loadApiKey();
```

Overall, this code is a simple example of how to interact with the OpenAI GPT-3 API using Java. It demonstrates how to set up an HTTP connection, make a POST request, process the response, and save the results to a database.

<br>

### Email.java:
The provided code is a Java class named `Email` that is part of the `htlle.mailresponse` package. This class is annotated with `@Entity`, indicating that it is a JPA entity. JPA (Java Persistence API) is a specification for object-relational mapping in Java. It allows us to map our domain objects to the underlying relational database.

```java
@Entity
public class Email {
```

The `Email` class has several private fields: `id`, `receiver`, `sender`, `subject`, `content`, and `timestamp`. The `id` field is annotated with `@Id`, indicating that it is the primary key of the `Email` entity.

```java
@Id
private Long id;
```

The class has a constructor that takes all the fields as parameters and assigns them to the corresponding instance variables. This constructor is used to create a new `Email` object with the specified values.

```java
public Email(Long id, String receiver, String sender, String subject, String content, Timestamp timestamp) {
```

There is also a no-argument constructor, which is required by JPA. This constructor is used by JPA to create instances of the entity class.

```java
public Email() {
```

The class also includes getter and setter methods for all the fields. These methods allow other classes to interact with the `Email` object's state.

```java
public String getReceiver() {
    return receiver;
}
```

In summary, the `Email` class is a simple JPA entity that represents an email. It includes fields for the email's id, receiver, sender, subject, content, and timestamp, as well as methods to get and set these values.

<br>

### EmailDatabase.java:
The provided code is a Java class named `EmailDatabase` that is part of the `htlle.mailresponse` package. This class is responsible for interacting with a H2 database to store and retrieve `Email` objects.

The `EmailDatabase` class has a private `Connection` field named `connection`, which is used to establish a connection with the database.

```java
private final Connection connection;
```

The `EmailDatabase` constructor initializes this connection using the `DriverManager.getConnection` method with the database URL, username, and password.

```java
public EmailDatabase() throws SQLException {
    connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    initDatabase();
}
```

The `initDatabase` method is called within the constructor to ensure that the `emails` table exists in the database. This method executes a SQL `CREATE TABLE IF NOT EXISTS` statement.

```java
private void initDatabase() throws SQLException {
    try (Statement stmt = connection.createStatement()) {
        stmt.execute("CREATE TABLE IF NOT EXISTS emails (id INT AUTO_INCREMENT PRIMARY KEY, receiver VARCHAR(255), sender VARCHAR(255), subject VARCHAR(255), content TEXT, timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
    }
}
```

The `insertEmail` method is used to insert a new email into the `emails` table. It prepares a SQL `INSERT INTO` statement with the receiver, sender, subject, and content of the email, and then executes the statement.

```java
public void insertEmail(String receiver, String sender, String subject, String content) throws SQLException {
    String sql = "INSERT INTO emails (receiver, sender, subject, content) VALUES (?, ?, ?, ?)";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, receiver);
        pstmt.setString(2, sender);
        pstmt.setString(3, subject);
        pstmt.setString(4, content);
        pstmt.executeUpdate();
    }
}
```

The `getAllEmails` method retrieves all emails from the `emails` table. It prepares a SQL `SELECT * FROM` statement, executes the statement, and processes the result set to create a list of `Email` objects.

```java
public List<Email> getAllEmails() throws SQLException {
    List<Email> emails = new ArrayList<>();
    String sql = "SELECT * FROM emails";
    try (PreparedStatement pstmt = connection.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            Email email = new Email(
                    rs.getLong("id"),
                    rs.getString("receiver"),
                    rs.getString("sender"),
                    rs.getString("subject"),
                    rs.getString("content"),
                    rs.getTimestamp("timestamp")
            );
            emails.add(email);
        }
    }
    return emails;
}
```

In summary, the `EmailDatabase` class is a simple example of how to interact with a H2 database using JDBC in Java. It demonstrates how to establish a connection, create a table, insert records, and retrieve records.

<br>

### EmailRepository.java:
The provided code is a Java interface named `EmailRepository` that is part of the `htlle.mailresponse` package. This interface extends `JpaRepository`, which is a part of the Spring Data JPA framework. Spring Data JPA is a sub-project of Spring Data that aims to simplify the implementation of data access layers by using JPA (Java Persistence API).

```java
public interface EmailRepository extends JpaRepository<Email, Long> {
```

The `EmailRepository` interface does not declare any methods. This is because `JpaRepository` provides a lot of methods out of the box for CRUD (Create, Read, Update, Delete) operations, such as `save`, `findAll`, `findById`, `delete`, etc. These methods can be used to interact with the database without having to write any implementation code.

The `JpaRepository` interface takes two parameters: the type of the entity and the type of the entity's primary key. In this case, `Email` is the entity type and `Long` is the type of the primary key.

```java
public interface EmailRepository extends JpaRepository<Email, Long> {
```

In summary, the `EmailRepository` interface is a simple example of a Spring Data JPA repository. It extends `JpaRepository` to gain access to a wide range of methods for interacting with the database. This interface can be injected into other Spring components using Spring's dependency injection features, allowing those components to easily perform database operations.

<br>

### EmailUtils.java:
The provided code is a Java utility class named `EmailUtil` that is part of the `htlle.mailresponse` package. This class is used to send an email using the JavaMail API.

The `EmailUtil` class has a single static method, `sendEmail`, which takes a `Session`, `toEmail`, `fromEmail`, `subject`, and `body` as parameters. This method is used to send an email with the specified parameters.

```java
public static void sendEmail(Session session, String toEmail, String fromEmail, String subject, String body){
```

Inside the `sendEmail` method, a `MimeMessage` object is created using the provided `Session`. The `MimeMessage` class is a part of the JavaMail API and represents an email message.

```java
MimeMessage msg = new MimeMessage(session);
```

Several headers are set on the `MimeMessage` object, including the content type, format, and content transfer encoding. These headers specify the format and encoding of the email.

```java
msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
msg.addHeader("format", "flowed");
msg.addHeader("Content-Transfer-Encoding", "8bit");
```

The `setFrom`, `setReplyTo`, `setSubject`, `setText`, and `setSentDate` methods are used to set the from address, reply-to address, subject, body, and sent date of the email, respectively.

```java
msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
msg.setReplyTo(InternetAddress.parse(toEmail, false));
msg.setSubject(subject, "UTF-8");
msg.setText(body, "UTF-8");
msg.setSentDate(new Date());
```

The `setRecipients` method is used to set the recipients of the email. The `Transport.send` method is then used to send the email.

```java
msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
Transport.send(msg);
```

In summary, the `EmailUtil` class is a simple utility class that uses the JavaMail API to send an email. It demonstrates how to create a `MimeMessage`, set various properties on the message, and send the message.

<br>

### MailResponseApplication.java:
The provided code is a Java class named `MailresponseApplication` that is part of the `htlle.mailresponse` package. This class is the entry point of a Spring Boot application.

The `MailresponseApplication` class is annotated with `@SpringBootApplication`, which is a convenience annotation that adds all of the following:

- `@Configuration`: Tags the class as a source of bean definitions for the application context.
- `@EnableAutoConfiguration`: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
- `@ComponentScan`: Tells Spring to look for other components, configurations, and services in the `htlle.mailresponse` package, allowing it to find and register other relevant components.

```java
@SpringBootApplication
public class MailresponseApplication {
```

The `main` method is the entry point of the Java application. When the application is started, this method is the first one that gets executed. It delegates to Spring Boot's `SpringApplication` class by calling `run`. `SpringApplication.run` bootstraps the application, creating the Spring application context and starting all the services.

```java
public static void main(String[] args) {
  SpringApplication.run(MailresponseApplication.class, args);
```

After the application has started, a message is printed to the console indicating that the server is running and listening for HTTP requests on `http://localhost:8080/`.

```java
System.out.println("Server running at http://localhost:8080/");
```

In summary, the `MailresponseApplication` class is a typical Spring Boot application entry point. It sets up and starts the Spring application context, and then indicates that the server is running.

<br>

### ReceiveMail.java:
The provided code is a Java class named `ReceiveMail` that is part of the `htlle.mailresponse` package. This class is used to receive emails from an Outlook account using the JavaMail API.

The `ReceiveMail` class has two private fields, `accountEmail` and `password`, which are used to authenticate with the Outlook account.

```java
private String accountEmail;
private String password;
```

The `receiveEmails` method is used to receive emails from the account. It first sets up the properties for the email session, including the protocol, host, port, and SSL settings.

```java
Properties properties = new Properties();
properties.put("mail.store.protocol", "imaps");
properties.put("mail.imaps.host", host);
properties.put("mail.imaps.port", "993");
properties.put("mail.imaps.ssl.enable", "true");
properties.put("mail.imaps.ssl.protocols", "TLSv1.2");
properties.put("mail.imaps.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256");
```

It then creates the email session and connects to the IMAP store using the account email and password.

```java
Session emailSession = Session.getInstance(properties);
Store store = emailSession.getStore("imaps");
store.connect(host, accountEmail, password);
```

The method retrieves the messages from the Inbox folder, and then closes the store and folder.

```java
Folder inbox = store.getFolder("INBOX");
inbox.open(Folder.READ_ONLY);
Message[] messages = inbox.getMessages();
inbox.close(false);
store.close();
```

The `getTextMessageContent` method is a helper method used to get the text content of a message. It handles different types of content, including `String` and `Multipart`.

```java
private String getTextMessageContent(Message message) throws Exception {
    Object content = message.getContent();
    if (content instanceof String) {
        return (String) content;
    } else if (content instanceof Multipart) {
        Multipart multipart = (Multipart) content;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (bodyPart.getContentType().startsWith("text/plain")) {
                result.append(getTextMessageContent(bodyPart));
            }
        }
        return result.toString();
    } else {
        return "Unsupported message type";
    }
}
```

In summary, the `ReceiveMail` class is a simple example of how to receive emails from an Outlook account using the JavaMail API. It demonstrates how to set up an email session, connect to an IMAP store, retrieve messages from the Inbox, and extract the text content of the messages.

<br>

### SendMail.java:
The provided code is a Java class named `SendMail` that is part of the `htlle.mailresponse` package. This class is used to send emails from an Outlook account using the JavaMail API.

The `SendMail` class has two private fields, `fromEmail` and `emailPassword`, which are used to authenticate with the Outlook account.

```java
private String fromEmail;
private String emailPassword;
```

The `sendEmail` method is used to send an email from the account. It first sets up the properties for the email session, including the SMTP host, port, authentication, and SSL settings.

```java
Properties props = new Properties();
props.put("mail.smtp.host", "smtp-mail.outlook.com");
props.put("mail.smtp.port", "587");
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");
props.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");
props.put("mail.smtp.ssl.protocols", "TLSv1.2");
props.put("mail.smtp.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256");
```

It then creates the email session and authenticates with the Outlook account using the `fromEmail` and `emailPassword` fields.

```java
Authenticator auth = new Authenticator() {
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(fromEmail, emailPassword);
    }
};
Session session = Session.getInstance(props, auth);
```

The `EmailUtil.sendEmail` method is then called to send the email. This method takes the email session, to email, from email, subject, and message as parameters.

```java
EmailUtil.sendEmail(session, toEmail, fromEmail, subject, message);
```

In summary, the `SendMail` class is a simple example of how to send emails from an Outlook account using the JavaMail API. It demonstrates how to set up an email session, authenticate with an Outlook account, and send an email.

<br>
<br>

## Documentation - Frontend - 01.12.2023:
### index.html:
The provided code is an HTML document that serves as the main interface for an email client application. The document structure is defined using standard HTML tags such as `<!DOCTYPE html>`, `<html>`, `<head>`, and `<body>`.

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Client</title>
    <link rel="stylesheet" href="style.css">
</head>
```

The `<head>` section includes meta tags for character encoding and viewport settings, a title for the webpage, and a link to an external CSS stylesheet named `style.css`.

The `<body>` section contains the main content of the webpage. It includes a `div` with the class `app`, which serves as a container for the entire application.

```html
<body>
<div class="app">
```

Inside the `app` div, there is a `flex` element that contains two sidebars: one for accounts and one for emails. Each sidebar includes a list (`ul`) of items (`li`).

```html
<flex class="flex-for-sidebars">
    <div class="sidebar-for-accounts">
        <div class="accounts-list">
            <h2>Accounts</h2>
            <ul id="account-list">
                <li>HTL1@htl-leoben.at</li>
                <li>HTL2@htl-leoben.at</li>
            </ul>
        </div>
    </div>
```

The main section of the application includes a header with a title, a list of messages, a section for entering the recipient and subject of an email, and a section for entering the body of the email.

```html
<div class="main">
    <div class="header">
        <h1>Email Client</h1>
    </div>
    <div class="message-list" id="message-list">
        <p>Message 1</p>
    </div>
    <div class="personTo">
        <input placeholder="To:">
        <input placeholder="Subject:">
        <button>ChatGPT Hilfe</button>
        <label class="file-input-button" id="uploadLabel">
            <input type="file" id="myFile" name="myFile" class="file-input" onclick="uploadFile()">
            <img src="resources.images/file-upload-duotone.svg" alt="Datei Auswählen">
        </label>
    </div>
    <div class="message-input">
        <textarea placeholder="Nachricht eingeben"></textarea>
        <button>Senden</button>
    </div>
</div>
```

Finally, the document includes a script tag at the end of the body that links to an external JavaScript file named `script.js`.

```html
<script src="script.js"></script>
</body>
</html>
```

In summary, this HTML document provides a user interface for an email client application. It includes sections for displaying accounts and emails, composing new emails, and interacting with the application.

<br>

### script.js:
The provided code is a JavaScript file named `script.js` that is part of the static resources of a Spring Boot application. This script is responsible for simulating the addition of new emails to an email client interface and handling user interactions with the interface.

The script begins by adding an event listener for the `DOMContentLoaded` event. This event is fired when the initial HTML document has been completely loaded and parsed, without waiting for stylesheets, images, and subframes to finish loading.

```javascript
document.addEventListener("DOMContentLoaded", function () {
```

Inside the event listener, a function named `addNewEmail` is defined. This function takes an email subject, content, and sender as parameters, and adds a new email to the email list in the sidebar of the interface.

```javascript
function addNewEmail(emailSubject, emailContent, emailSender) {
```

The function creates a new list item (`li`), sets its text content to the email subject, and appends it to the email list. It also adds a click event listener to the list item, which calls the `openEmail` function when the item is clicked.

```javascript
listItem.addEventListener("click", function () {
    openEmail(emailSubject, emailContent, emailSender);
});
```

The script then simulates the addition of new emails by calling the `addNewEmail` function in a loop. Each iteration of the loop is delayed by 2 seconds using the `setTimeout` function.

```javascript
for (let i = 1; i <= 10; i++) {
    setTimeout(function () {
        const subject = "Neue E-Mail " + i;
        const content = "Inhalt der E-Mail " + i;
        const sender = "Name " + i;
        addNewEmail(subject, content, sender);
    }, i * 2000);
}
```

The `openEmail` function is used to display an email in the main area of the interface. It takes an email subject, content, and sender as parameters, and creates and appends new HTML elements to the message list to display these details.

```javascript
function openEmail(emailSubject, emailContent, emailSender) {
```

Finally, the `uploadFile` function is defined. This function retrieves the file selected by the user in the file input element, and appends it to a new `FormData` object. This object can then be used to send the file to the server in an HTTP request.

```javascript
function uploadFile() {
    var fileInput = document.getElementById('myFile');
    var file = fileInput.files[0];
    var formData = new FormData();
    formData.append('file', file);
}
```

In summary, the `script.js` file is a simple example of how to simulate the addition of new emails to an email client interface, handle user interactions with the interface, and prepare a file for upload using JavaScript.

<br>

### style.css:
The provided code is a CSS stylesheet named `style.css` used for styling a web page in a Spring Boot application. CSS (Cascading Style Sheets) is a language used for describing the look and formatting of a document written in HTML. This stylesheet would contain rules for how to display HTML elements. Each rule or rule-set consists of one or more selectors and a declaration block. In the declaration block, you can specify the styling properties such as color, font-size, width, background-color, etc. Unfortunately, without the actual content of the `style.css` file, I can't provide specific details about the styles applied in this case.

<br>
<br>

## Documentation - Serverconf/ Database - 01.12.2023:
### application.properties:
The provided code is a properties file named `application.properties` in a Spring Boot application. This file is used to configure application-wide settings.

The first section of the file configures the H2 database connection. The `spring.datasource.url` property sets the JDBC URL for the H2 database. The `spring.datasource.driver-class-name` property specifies the JDBC driver to use. The `spring.datasource.username` and `spring.datasource.password` properties set the username and password to connect to the database.

```properties
spring.datasource.url=jdbc:h2:tcp://localhost/~/h2_db/MailResponse
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

The `spring.jpa.database-platform` property sets the Hibernate dialect to use. The dialect is a configuration setting in Hibernate that makes it compatible with the used database.

```properties
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

The `spring.h2.console.enabled` property enables the H2 console, which is a web application that provides a convenient interface for managing the H2 database.

```properties
spring.h2.console.enabled=true
```

The `spring.jpa.hibernate.ddl-auto` property configures the Hibernate's database schema generation. The `update` value means that Hibernate will update the schema if necessary when the application starts.

```properties
spring.jpa.hibernate.ddl-auto=update
```

In summary, the `application.properties` file is used to configure the database connection, Hibernate settings, and other application-wide settings in a Spring Boot application.

<br>

### Database:
The `MailResponse.mv.db` file is the database file for the H2 database used in the application. H2 is a lightweight, in-memory database written in Java. The file stores all the data of the application, including tables, records, and other database objects. The data in this file is manipulated through the application using SQL queries and JDBC.

<br>

### pom.xml:
The project `MailResponses` is a Java-based application built with Spring Boot and Maven. It interacts with the OpenAI GPT-3 API to handle email responses. The backend, written in Java, includes classes for interacting with the OpenAI API, managing emails, and handling database operations. The frontend, written in HTML, CSS, and JavaScript, provides a user interface for the email client application.

Key backend classes include `ChatGPT.java` for interacting with the OpenAI API, `Email.java` as a JPA entity representing an email, `EmailDatabase.java` for database operations, and `EmailUtil.java` for sending emails using JavaMail API.

The frontend includes `index.html` for the user interface, `script.js` for handling user interactions and simulating email addition, and `style.css` for styling the webpage.

The `application.properties` file configures the H2 database connection and Hibernate settings. The `MailResponse.mv.db` file is the H2 database file storing all the data of the application. The `pom.xml` file is a Maven configuration file specifying the project's dependencies and build settings.

<br>

