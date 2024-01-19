package htlle.mailresponse.model;

import htlle.mailresponse.Mail.EmailDummy;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<EmailDummy> emails = new ArrayList<>();

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<EmailDummy> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailDummy> emails) {
        this.emails = emails;
    }

    public void addEmail(EmailDummy email) {
        this.emails.add(email);
    }
}
