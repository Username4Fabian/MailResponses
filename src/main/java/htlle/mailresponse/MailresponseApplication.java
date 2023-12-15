package htlle.mailresponse;

import htlle.mailresponse.ChatGPT.ChatGPT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class MailresponseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailresponseApplication.class, args);
        System.out.println("Server running at http://localhost:8080/");

        Scanner scanner = new Scanner(System.in);
        String responseMood = "Antworte auf diese Email in einem aggresiven beleidigenden Stil";

        System.out.println("Enter text:");
        String text = scanner.nextLine();

        try {
            ChatGPT.chatGPT(responseMood, text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        scanner.close();
    }

}