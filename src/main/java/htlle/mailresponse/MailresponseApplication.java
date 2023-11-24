package htlle.mailresponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailresponseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailresponseApplication.class, args);
		System.out.println("Server running at http://localhost:8080/");
	}

}
