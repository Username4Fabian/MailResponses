package htlle.mailresponse;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class sendGetMail
{
    public static void sendMail( String betreff, String inhalt, String empfaenger) throws MessagingException, SQLException {
        EmailDatabase db = new EmailDatabase();
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bikebuilder03@outlook.com", "hutter1234");
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom( new InternetAddress("bikebuilder03@outlook.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(empfaenger));
        message.setSubject(betreff, "ISO-8859-1" );
        message.setText(inhalt,"UTF-8" );
        Transport.send(message);
        db.insertEmail(empfaenger, "bikebuilder03@outlook.com",betreff,inhalt);
    }

    public static List<Email> receiveEmail(){
        List<Email> mails = new ArrayList<Email>();
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.imap.host", "outlook.office365.com"); // IMAP server for Outlook
        properties.put("mail.imap.port", "993");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bikebuilder03@outlook.com", "hutter1234 ");
            }
        });
            try {
                Store store = session.getStore();
                store.connect();
                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_ONLY);
            Message[] emailMessages = inbox.getMessages();
            for(int i = 0;i< emailMessages.length;i++) {
                mails.add(new Email(emailMessages[i].getMessageNumber(),emailMessages[i].getAllRecipients().toString()
                        ,emailMessages[i].getFrom().toString(),emailMessages[i].getSubject(),emailMessages[i].getContent().toString()
                        ,(Timestamp) emailMessages[i].getSentDate()));
            }
                inbox.close(false);
                store.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in receiving email.");
        }
        return mails;
    }
}