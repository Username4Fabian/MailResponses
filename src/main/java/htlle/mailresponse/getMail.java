package htlle.mailresponse;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class getMail
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
        SendMail sendMail = new SendMail();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.imap.host", "outlook.office365.com"); // IMAP server for Outlook
        properties.put("mail.imap.port", "993");
        try {
            // Sitzung erstellen
            Session session = Session.getDefaultInstance(properties);

            // IMAP-Store öffnen
            Store store = session.getStore("imaps");
            store.connect(sendMail., username, password);

            // Ordner auswählen (z.B., "INBOX" für Posteingang)
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // E-Mails abrufen und ausgeben
            Message[] messages = inbox.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
                System.out.println("------------------------");
            }

            // Ordner und Store schließen
            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mails;
    }
}