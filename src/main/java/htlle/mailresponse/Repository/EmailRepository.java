package htlle.mailresponse.Repository;

import htlle.mailresponse.Mail.EmailDummy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailDummy, Integer> {
    EmailDummy findById(int id);
    // This interface is used to access the database.
}
