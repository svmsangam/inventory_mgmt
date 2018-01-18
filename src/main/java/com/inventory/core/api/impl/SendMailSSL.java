package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ISendMailSSL;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by dhiraj on 1/18/18.
 */
@Service
public class SendMailSSL implements ISendMailSSL {

    private Properties getProperty() {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        return props;
    }

    private Session getSesseion(Properties props) {

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("dhirajbadu50@gmail.com","De@m0n5o");
                    }
                });

        return session;
    }

    @Override
    public void sendMail(String from, String to, String msg, String sub) {

        try {

            Properties properties = getProperty();

            Session session = getSesseion(properties);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(sub);
            message.setText(msg);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
