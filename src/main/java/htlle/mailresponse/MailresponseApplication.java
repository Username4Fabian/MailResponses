package htlle.mailresponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is the entry point of the application.
 * It uses Spring Boot's SpringApplication.run() method to launch the application.
 */
@SpringBootApplication
public class MailresponseApplication {

    /**
     * The main method which serves as the entry point for the JVM.
     * The SpringApplication.run() method is called to bootstrap the application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(MailresponseApplication.class, args);
        System.out.println("Application running at http://localhost:8080");
    }
}