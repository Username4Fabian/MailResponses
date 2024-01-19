package htlle.mailresponse.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import htlle.mailresponse.Repository.EmailRepository;
import htlle.mailresponse.Repository.UserRepository;
import htlle.mailresponse.model.User;

import javax.mail.*;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ReceiveMail {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository for app_user table

    public ReceiveMail() {
        // Schedule the task to run every minute
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::receiveEmails, 0, 10, TimeUnit.SECONDS);
    }

    public void receiveEmails() {
        // Fetch the user from the database
        User user = userRepository.findById(1);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        // Set the Outlook email account details
        String host = "outlook.office365.com";
        String accountEmail = user.getEmail();
        String password = user.getPassword();

        System.out.println(accountEmail + password);

        // Set the properties for the email session
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", host);
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true"); // Use this property
        properties.put("mail.imaps.ssl.trust", "*"); // Trust all hosts
        properties.put("mail.imaps.ssl.protocols", "TLSv1.2"); // Specify the appropriate TLS version
        properties.put("mail.imaps.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"); // Specify an appropriate cipher suite

        try {
            // Create the email session
            Session emailSession = Session.getInstance(properties);
            // Create the IMAP store
            Store store = emailSession.getStore("imaps");
            store.connect(host, accountEmail, password);
            System.out.println("Connected to the email server");

            // Get the Inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            System.out.println("Opened the inbox");

            // Get the messages from the Inbox
            Message[] messages = inbox.getMessages();
            System.out.println("Fetched " + messages.length + " messages from the inbox");

            // Iterate over the messages and save each one to the database
            for (Message message : messages) {
                EmailDummy emailDummy = new EmailDummy();
                emailDummy.setReceiver(message.getRecipients(Message.RecipientType.TO)[0].toString());
                emailDummy.setSubject(message.getSubject());
                emailDummy.setContent(message.getContent().toString());
                emailRepository.save(emailDummy);
                System.out.println("Saved a message to the database");
            }

            // Close the store and folder
            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}