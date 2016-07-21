package edu.tsu.stochastic.automats.core.formula;


import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailTest {

    private String userName = "baakazzz@gmail.com";
    private String password = "adidass898";

    @Test
    public void test() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("baakazzz@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("baaka2@hotmail.com"));
            message.setSubject("Test Subject");
            message.setText("Dear Me, \n Hi there!");

            Transport.send(message);
            System.out.println("done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
