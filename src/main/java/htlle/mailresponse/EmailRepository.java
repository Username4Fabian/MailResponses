package htlle.mailresponse;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
    // This interface is used to access the database.
}
