package htlle.mailresponse.Mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class SendMail {

    public static void sendEmail(EmailDummy emailDummy) {
        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "mailrelay.edis.at"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.smtp.ssl.trust", "*"); // Trust all hosts
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Specify the appropriate TLS version
        props.put("mail.smtp.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"); // Specify an appropriate cipher suite

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println(emailDummy.getUser().getPassword());
                System.out.println(emailDummy.getUser().getEmail());
                return new PasswordAuthentication(emailDummy.getUser().getEmail(), emailDummy.getUser().getPassword());
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, emailDummy.getReceiver(), emailDummy.getUser().getEmail(), emailDummy.getSubject(), emailDummy.getContent());

    }
}