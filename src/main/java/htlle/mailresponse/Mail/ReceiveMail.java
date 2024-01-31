package htlle.mailresponse;

import htlle.mailresponse.Mail.EmailDummy;
import htlle.mailresponse.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;

public class ReceiveMail {

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

        try {
            // Create the email session
            Session emailSession = Session.getInstance(properties);
            //emailSession.setDebug(true); // Enable debugging

            // Create the IMAP store
            Store store = emailSession.getStore("imaps");
            store.connect(host, accountEmail, password);

            // Get the Inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Get the messages from the Inbox
            Message[] messages = inbox.getMessages();

            List<EmailDummy> emails = new ArrayList<>();

            for (int i = 0; i < messages.length; i++) {
                EmailDummy emailDummy = new EmailDummy(messages[i].getFrom()[0].toString(), messages[i].getSubject(), getTextMessageContent(messages[i]), user, new Timestamp(messages[i].getSentDate().getTime()));
                System.out.println(emailDummy.getContent());
                emails.add(emailDummy);
            }


            // Print details of each message
            /*
            for (int i = 0; i < messages.length; i++) {
                System.out.println("Subject: " + messages[i].getSubject());
                System.out.println("From: " + messages[i].getFrom()[0]);
                System.out.println("Date: " + messages[i].getSentDate());
                System.out.println("Message: " + getTextMessageContent(messages[i]));
                System.out.println("---------------------------------------------");
            }
             */

            // Close the store and folder
            inbox.close(false);
            store.close();


            return emails;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Helper method to get the text content of a message
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

    // Helper method to get the text content of a body part
    private static String getTextMessageContent(BodyPart bodyPart) throws Exception {
        Object content = bodyPart.getContent();
        if (content instanceof String) {
            return (String) content;
        } else {
            return "Unsupported body part type";
        }
    }
}