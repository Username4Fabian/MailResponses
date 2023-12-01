package htlle.mailresponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "Email", path = "Email")
public interface EmailRepository extends JpaRepository<Email, Long> {
    @Query(value = "SELECT RECEIVER, SENDER, SUBJECT, CONTENT FROM EMAILS ", nativeQuery = true)
    List<Email> getEmailsFromServer();
}
