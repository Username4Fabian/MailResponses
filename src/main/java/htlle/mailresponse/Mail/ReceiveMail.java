package htlle.mailresponse.Mail;

import htlle.mailresponse.model.User;

import javax.mail.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class is responsible for receiving emails.
 */
public class ReceiveMail {

    /**
     * Receives emails for a specific user.
     *
     * @param user The user to receive emails for.
     * @return A list of received emails.
     */
    public static List<EmailDummy> receiveEmails(User user) {
        // Set the Outlook email account details
        String host = "mail.edis.at";
        String accountEmail = user.getEmail();
        String password = user.getPassword();

        // Set the properties for the email session
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", host);
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true"); // Use this property
        properties.put("mail.imaps.ssl.protocols", "TLSv1.2"); // Specify the appropriate TLS version
        properties.put("mail.imaps.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"); // Specify an appropriate cipher suite

        List<EmailDummy> emails = new ArrayList<>();

        try {
            // Create the email session
            Session emailSession = Session.getInstance(properties);

            // Create the IMAP store
            Store store = emailSession.getStore("imaps");
            store.connect(host, accountEmail, password);

            // Get the Inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Get the messages from the Inbox
            Message[] messages = inbox.getMessages();

            for (Message message : messages) {
                EmailDummy emailDummy = new EmailDummy(message.getFrom()[0].toString(), message.getSubject(), getTextMessageContent(message), user, new Timestamp(message.getSentDate().getTime()));
                emails.add(emailDummy);
            }

            // Close the store and folder
            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emails;
    }

    /**
     * Helper method to get the text content of a message.
     *
     * @param message The message to get the content from.
     * @return The text content of the message.
     * @throws Exception If there is an error while getting the content.
     */
    private static String getTextMessageContent(Message message) throws Exception {
        Object content = message.getContent();
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getContentType().startsWith("text/plain")) {
                    result.append(getTextMessageContent(bodyPart));
                }
            }

            return result.toString();
        } else {
            return "Unsupported message type";
        }
    }

    /**
     * Helper method to get the text content of a body part.
     *
     * @param bodyPart The body part to get the content from.
     * @return The text content of the body part.
     * @throws Exception If there is an error while getting the content.
     */
    private static String getTextMessageContent(BodyPart bodyPart) throws Exception {
        Object content = bodyPart.getContent();
        if (content instanceof String) {
            return (String) content;
        } else {
            return "Unsupported body part type";
        }
    }
}