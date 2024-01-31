package htlle.mailresponse.Repository;

import htlle.mailresponse.Mail.EmailDummy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface represents a repository for EmailDummy entities.
 * It extends JpaRepository to provide methods to manipulate EmailDummy entities.
 */
public interface EmailRepository extends JpaRepository<EmailDummy, Integer> {

    /**
     * Finds an EmailDummy entity by its ID.
     *
     * @param id The ID of the EmailDummy entity to find.
     * @return The found EmailDummy entity, or null if no entity with the given ID exists.
     */
    EmailDummy findById(int id);
}