package htlle.mailresponse.Mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * This class is responsible for sending emails.
 */
public class SendMail {

    /**
     * Sends an email using the provided email dummy.
     *
     * @param emailDummy The email dummy to send.
     */
    public static void sendEmail(EmailDummy emailDummy) {
        System.out.println("TLSEmail Start");

        // Set the SMTP server details
        Properties props = getProperties();

        // Create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            // Override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailDummy.getUser().getEmail(), emailDummy.getUser().getPassword());
            }
        };

        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, emailDummy.getReceiver(), emailDummy.getUser().getEmail(), emailDummy.getSubject(), emailDummy.getContent());
    }

    /**
     * Sets the properties for the email session.
     *
     * @return The properties for the email session.
     */

    // Set "reply to" tag
    private static Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "mailrelay.edis.at"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true"); // Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS
        props.put("mail.smtp.ssl.trust", "*"); // Trust all hosts
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Specify the appropriate TLS version
        props.put("mail.smtp.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"); // Specify an appropriate cipher suite
        return props;
    }
}