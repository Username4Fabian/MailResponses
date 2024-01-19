package htlle.mailresponse.Controller;

import htlle.mailresponse.Repository.UserRepository;
import htlle.mailresponse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("createUser")
    public void createUser(String email, String password) {
        User user = new User(email, password);
        userRepository.save(user);
    }
}
