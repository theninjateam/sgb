package sgb.email;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sgb.controller.domainController.ConfigControler;
import sgb.deadline.MiniBookingDeadline;

import javax.mail.Session;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Meneses
 */



public class SendEmailTest
{
    @Autowired
    private ApplicationContext context;


    private sgb.email.PasswordAuthenticatior passwordAuthenticatior;
    private SendEmail sendEmail;
    Properties props = new Properties();
    Session session;


    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");
        this.sendEmail = (SendEmail) context.getBean("sendEmail");
        this.passwordAuthenticatior = (PasswordAuthenticatior)context.getBean("passwordAuthenticator");


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

    @Test
    @Transactional
    public void sendEmailTest() throws Exception
    {

        boolean ifSend;


        try {
            URL url = new URL("http://www.google.co.mz/");

            URLConnection connection = url.openConnection();

            HttpURLConnection urlConnection = (HttpURLConnection) connection;
            urlConnection.connect();

            int x = urlConnection.getResponseCode();


            if (x ==200){

                ifSend= sendEmail.sendEmail("","","ninjateam@unilurio.ac.mz");
                assertThat(ifSend).isTrue();
            }

        }catch (Exception ex){

            ifSend= sendEmail.sendEmail("","","ninjateam@unilurio.ac.mz");
            assertThat(ifSend).isFalse();
        }

    }


}