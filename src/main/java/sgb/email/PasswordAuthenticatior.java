package sgb.email;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class PasswordAuthenticatior extends Authenticator {

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
