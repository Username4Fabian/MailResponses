package htlle.mailresponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;
@Entity
public class Email {
    @Id
    private Long id;
    private String receiver;
    private String sender;
    private String subject;
    private String content;
    private Timestamp timestamp;

    public Email(Long id, String receiver, String sender, String subject, String content, Timestamp timestamp) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
        this.subject = subject;
        this.timestamp = timestamp;
    }

    public Email() {

    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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

}
