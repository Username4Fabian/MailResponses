package htlle.mailresponse.Mail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailDummy, Long> {
    EmailDummy findById(int id);
    // This interface is used to access the database.
}
