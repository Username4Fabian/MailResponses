package htlle.mailresponse.Controller;

import htlle.mailresponse.Mail.EmailDummy;
import htlle.mailresponse.Mail.ReceiveMail;
import htlle.mailresponse.Mail.SendMail;
import htlle.mailresponse.Repository.EmailRepository;
import htlle.mailresponse.Repository.UserRepository;
import htlle.mailresponse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is responsible for handling requests related to the Email functionality.
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * This method handles GET requests to retrieve all emails.
     *
     * @return A list of all emails.
     */
    @GetMapping("/getAllEmails")
    public List<EmailDummy> getAllEmails() {
        return emailRepository.findAll();
    }

    /**
     * This method handles GET requests to retrieve an email by its ID.
     *
     * @param id The ID of the email.
     * @return The email with the given ID.
     */
    @GetMapping("/getEmailById/{id}")
    public EmailDummy getEmailById(@PathVariable("id") int id) {
        return emailRepository.findById(id);
    }

    /**
     * This method handles POST requests to send an email.
     *
     * @param userId  The ID of the user sending the email.
     * @param to      The recipient of the email.
     * @param subject The subject of the email.
     * @param text    The text of the email.
     * @return The sent email.
     */
    @PostMapping("/sendEmail")
    public EmailDummy sendEmail(@RequestParam int userId, @RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        User user = userRepository.findById(userId);
        EmailDummy emailDummy = new EmailDummy(to, subject, text, user);
        SendMail.sendEmail(emailDummy);
        emailRepository.save(emailDummy);
        return emailDummy;
    }

    /**
     * This method handles POST requests to receive emails.
     *
     * @param userId The ID of the user receiving the emails.
     * @return A list of received emails.
     */
    @PostMapping("/receiveEmails")
    public List<EmailDummy> refreshEmails(@RequestParam int userId) {
        User user = userRepository.findById(userId);
        return ReceiveMail.receiveEmails(user);
    }
}