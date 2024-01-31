package htlle.mailresponse.Repository;

import htlle.mailresponse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface represents a repository for User entities.
 * It extends JpaRepository to provide methods to manipulate User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a User entity by its ID.
     *
     * @param userId The ID of the User entity to find.
     * @return The found User entity, or null if no entity with the given ID exists.
     */
    User findById(int userId);
}