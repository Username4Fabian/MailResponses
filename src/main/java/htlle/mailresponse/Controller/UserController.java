package htlle.mailresponse.Controller;

import htlle.mailresponse.Repository.UserRepository;
import htlle.mailresponse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is responsible for handling requests related to the User functionality.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * This method handles POST requests to create a new user.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     */
    @PostMapping("/createUser")
    public void createUser(@RequestParam String email, @RequestParam String password) {
        User user = new User(email, password);
        userRepository.save(user);
    }

    /**
     * This method handles GET requests to retrieve all users.
     *
     * @return A list of all users.
     */
    @GetMapping("/getUser")
    public List<User> getUser() {
        return userRepository.findAll();
    }
}