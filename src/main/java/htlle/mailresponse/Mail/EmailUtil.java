package htlle.mailresponse.Mail;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * This class provides utility methods for sending emails.
 */
public class EmailUtil {

    /**
     * Sends an HTML email.
     *
     * @param session   The mail session.
     * @param toEmail   The recipient's email address.
     * @param fromEmail The sender's email address.
     * @param subject   The email subject.
     * @param body      The email body.
     */
    public static void sendEmail(Session session, String toEmail, String fromEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);

            // Set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, "bike builder"));
            msg.setReplyTo(InternetAddress.parse(toEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}