package sgb.email;

import static javax.mail.Message.RecipientType.TO;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail{


    Properties props = new Properties();
    Session session;

    public SendEmail() {


        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(
                props,
                new PasswordAuthenticatior("ninjateam@unilurio.ac.mz", "p(x)=7*ninja")
        );

    }

    class PasswordAuthenticatior extends Authenticator {

        private String username;
        private String password;

        PasswordAuthenticatior(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }


    public void sendEmail(String msg,String sbjt, String destination) throws MessagingException, IOException {

        try {

            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress("ninjateam@unilurio.ac.mz"));
            mimeMessage.setSender(new InternetAddress("ninjateam@unilurio.ac.mz"));

            mimeMessage.setRecipient(TO, new InternetAddress(destination));

            mimeMessage.setSubject(sbjt, "utf-8");
            mimeMessage.setText(msg);

          //  mimeMessage.writeTo(System.out);

            Transport.send(mimeMessage);

        } catch (Exception ex){

            System.out.println("errro mensagem nao enviada");
        }
    }

}
