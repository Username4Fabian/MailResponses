package htlle.mailresponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@Rollback(false)

public class EmailRepositoryTest {

    @Autowired
    private EmailRepository emailRepository;

    @BeforeEach
    public void setup() {
        // Cleanup any existing data (this step is optional but ensures a clean state for each test)
        emailRepository.deleteAll();
    }

    @Test
    public void testSaveAndFind() {
        // Create example dataset
        Email email1 = new Email();
        email1.setSenderAddress("sender1@example.com");
        email1.setRecipientAddresses(Arrays.asList("recipient1@example.com", "recipient2@example.com"));
        email1.setSubject("This is a Test Subject");
        email1.setMessageBody("Test Message 1");
        email1.setDateTime(LocalDateTime.now());
        email1.setRead(false);

        Email email2 = new Email();
        email2.setSenderAddress("sender2@example.com");
        email2.setRecipientAddresses(Arrays.asList("recipient3@example.com"));
        email2.setSubject("Test Subject 2");
        email2.setMessageBody("Test Message 2");
        email2.setDateTime(LocalDateTime.now().minusDays(1));
        email2.setRead(true);

        // Save to the database
        emailRepository.save(email1);
        emailRepository.save(email2);
        emailRepository.flush();  // explicitly flush changes

        // Retrieve and validate
        List<Email> emails = emailRepository.findAll();
        assertThat(emails).hasSize(2);

        Email retrievedEmail1 = emails.get(0);
        assertThat(retrievedEmail1.getSenderAddress()).isEqualTo("sender1@example.com");
        assertThat(retrievedEmail1.getSubject()).isEqualTo("This is a Test Subject");

        Email retrievedEmail2 = emails.get(1);
        assertThat(retrievedEmail2.getSenderAddress()).isEqualTo("sender2@example.com");
        assertThat(retrievedEmail2.getSubject()).isEqualTo("Test Subject 2");
    }

    @AfterEach
    public void tearDown() {
        // Optionally cleanup after each test, if you want to keep data, simply comment out or remove this
        //emailRepository.deleteAll();
    }
}
