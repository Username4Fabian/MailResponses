package htlle.mailresponse.Mail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import htlle.mailresponse.model.User;
import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * This class represents a dummy email entity.
 */
@Entity
public class EmailDummy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String receiver;

    @ManyToOne
    @JsonIgnoreProperties({"emails"})
    private User user;

    private String subject;
    private String content;
    private Timestamp timestamp;

    /**
     * Constructor with all parameters.
     *
     * @param receiver The receiver of the email.
     * @param subject The subject of the email.
     * @param content The content of the email.
     * @param user The user who sends the email.
     * @param timestamp The timestamp when the email is sent.
     */
    public EmailDummy(String receiver, String subject, String content, User user, Timestamp timestamp) {
        this.receiver = receiver;
        this.content = content;
        this.subject = subject;
        this.timestamp = timestamp;
        this.user = user;
    }

    /**
     * Constructor without timestamp. The timestamp will be set to the current time.
     *
     * @param receiver The receiver of the email.
     * @param subject The subject of the email.
     * @param content The content of the email.
     * @param user The user who sends the email.
     */
    public EmailDummy(String receiver, String subject, String content, User user) {
        this.user = user;
        this.receiver = receiver;
        this.content = content;
        this.subject = subject;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Default constructor.
     */
    public EmailDummy() {
    }

    // Getters and setters with standard Java naming conventions

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
}