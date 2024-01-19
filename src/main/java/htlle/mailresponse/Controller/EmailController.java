package htlle.mailresponse.Controller;

import htlle.mailresponse.Mail.EmailDummy;
import htlle.mailresponse.Repository.EmailRepository;
import htlle.mailresponse.Mail.SendMail;
import htlle.mailresponse.Repository.UserRepository;
import htlle.mailresponse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getAllEmails")
    public List<EmailDummy> getAllEmails() {
        return emailRepository.findAll();
    }

    @GetMapping("/getEmailById/{id}")
    public EmailDummy getEmailById(@PathVariable("id") int id) {
        return emailRepository.findById(id);
    }

    @PostMapping("/sendEmail")
    public EmailDummy sendEmail(@RequestParam int userId, @RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        User user = userRepository.findById(userId);
        EmailDummy emailDummy = new EmailDummy(to, subject, text, user);
        System.out.println(emailDummy.getContent());
        SendMail.sendEmail(emailDummy);
        emailRepository.save(emailDummy);
        return emailDummy;
    }
}
