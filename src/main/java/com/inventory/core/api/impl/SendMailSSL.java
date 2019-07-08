package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ISendMailSSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 * Created by dhiraj on 1/18/18.
 */
@Service
public class SendMailSSL implements ISendMailSSL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskExecutor taskExecutor;

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
                        return new PasswordAuthentication("inventory.sys.info@gmail.com", "deamon50");
                    }
                });

        return session;
    }

    @Override
    public void sendMail(String from, String to, String msg, String sub) {

        taskExecutor.execute( new Runnable() {
            public void run() {
                simpleMailHalper(from , to , msg , sub);
            }
        });

    }

    private void simpleMailHalper(String from, String to, String msg, String sub) {

        try {

            Properties properties = getProperty();

            Session session = getSesseion(properties);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(sub);
            message.setText(msg);

            try {
                Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Failed to send email to: " + to + " reason: "+e.getMessage());
            }

            System.out.println("Done");

        } catch (AddressException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());

            logger.error("error on mail send : " + e.getMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendHtmlMail(String from, String to, String msg, String sub) {

        taskExecutor.execute( new Runnable() {
            public void run() {
                htmlMailHelper(from, to, msg, sub);
            }
        });

    }

    private void htmlMailHelper(String from, String to, String msg, String sub) {

        try {

            Properties properties = getProperty();

            Session session = getSesseion(properties);

            Message message = new MimeMessage(session);
            Multipart multiPart = new MimeMultipart("alternative");

            //MimeBodyPart textPart = new MimeBodyPart();
            //textPart.setText(sub, "utf-8");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(msg, "text/html; charset=utf-8");

            //multiPart.addBodyPart(textPart); // <-- first
            multiPart.addBodyPart(htmlPart); // <-- second
            message.setContent(multiPart);

            multiPart.addBodyPart(htmlPart);
            //multiPart.addBodyPart(textPart);
            message.setContent(multiPart);

            if (from != null) {
                message.setFrom(new InternetAddress(from));
            } else
                message.setFrom();

            /*if (replyto != null)
                message.setReplyTo(new InternetAddress[]{new InternetAddress(replyto)});
            else*/
            message.setReplyTo(new InternetAddress[]{new InternetAddress(from)});

            InternetAddress[] toAddresses = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, toAddresses);
            message.setSubject(sub);
            message.setSentDate(new Date());

            Transport.send(message);

        } catch (AddressException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            logger.error("error on address exception mail send : " + e.getMessage());

        } catch (AuthenticationFailedException e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());

            logger.error("email address password miss matched : " + e.getMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            logger.error("error on MessagingException mail send : " + e.getMessage());

        }

        System.out.println("Email sent! to : " + to + " : by : " + from);
            logger.info("Email sent! to : " + to + " : by : " + from);

    }

}
