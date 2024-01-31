package htlle.mailresponse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import htlle.mailresponse.Mail.EmailDummy;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    @JsonIgnore
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