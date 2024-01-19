package htlle.mailresponse.Repository;

import htlle.mailresponse.Mail.EmailDummy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailDummyRepository extends JpaRepository<EmailDummy, Integer> {
    EmailDummy findById(int id);
}
