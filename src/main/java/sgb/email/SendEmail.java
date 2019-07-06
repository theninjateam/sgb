package sgb.email;

import static javax.mail.Message.RecipientType.TO;

import java.io.IOException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail{


    private sgb.email.PasswordAuthenticatior passwordAuthenticatior;
    Properties props = new Properties();
    Session session;

    public SendEmail(sgb.email.PasswordAuthenticatior passwordAuthenticatior) {

        this.passwordAuthenticatior = passwordAuthenticatior;
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(
                props, this.passwordAuthenticatior
        );

    }


    public boolean sendEmail(String sbjt, String msg, String destination) throws MessagingException {


        try{

                MimeMessage mimeMessage = new MimeMessage(session);

                mimeMessage.setFrom(new InternetAddress("ninjateam@unilurio.ac.mz"));
                mimeMessage.setSender(new InternetAddress("ninjateam@unilurio.ac.mz"));

                mimeMessage.setRecipient(TO, new InternetAddress(destination));

                mimeMessage.setSubject(sbjt, "utf-8");
                mimeMessage.setText(msg);

                Transport.send(mimeMessage);
                 return true;

            }catch (Exception e){
                return false;
            }



    }

}
