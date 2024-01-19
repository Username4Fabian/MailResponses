package htlle.mailresponse.Mail;
import htlle.mailresponse.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Timestamp;
@Entity
public class EmailDummy {
    @Id
    private Long id;
    private String receiver;
    @ManyToOne
    private User user;
    private String subject;
    private String content;
    private Timestamp timestamp;

    public EmailDummy(Long id, String receiver, String subject, String content, User user, Timestamp timestamp) {
        this.id = id;
        this.receiver = receiver;
        this.content = content;
        this.subject = subject;
        this.timestamp = timestamp;
        this.user = user;
    }

    public EmailDummy(String receiver, String subject, String content, User user) {
        this.user = user;
        this.receiver = receiver;
        this.content = content;
        this.subject = subject;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public EmailDummy() {

    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
