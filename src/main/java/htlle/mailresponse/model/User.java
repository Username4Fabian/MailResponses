package htlle.mailresponse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import htlle.mailresponse.Mail.EmailDummy;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a user entity.
 */
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

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructor with email and password parameters.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters with standard Java naming conventions

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

    /**
     * Adds an email to the user's email list.
     *
     * @param email The email to add.
     */
    public void addEmail(EmailDummy email) {
        this.emails.add(email);
    }
}