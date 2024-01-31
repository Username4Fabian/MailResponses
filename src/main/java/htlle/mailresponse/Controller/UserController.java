package htlle.mailresponse.Controller;

import htlle.mailresponse.Repository.UserRepository;
import htlle.mailresponse.Repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import htlle.mailresponse.model.User;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("createUser")
    public void createUser(String email, String password) {
        User user = new User(email, password);
        userRepository.save(user);
    }

    @GetMapping("getUser")
        public List<User> getUser (){
            return userRepository.findAll();
        }
    }
