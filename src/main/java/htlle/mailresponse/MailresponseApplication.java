package htlle.mailresponse;

import htlle.mailresponse.ChatGPT.ChatGPT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class MailresponseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailresponseApplication.class, args);
        System.out.println("Application running at http://localhost:8080");
    }

}