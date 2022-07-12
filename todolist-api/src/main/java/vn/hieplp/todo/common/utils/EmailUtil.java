package vn.hieplp.todo.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.config.EmailConfig;
import vn.hieplp.todo.common.exceptions.CommonException;
import vn.hieplp.todo.common.models.EmailModel;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 9/5/2022
 * Time: 21:7
 **/


public class EmailUtil {
    private static final Logger LOGGER = LogManager.getLogger(EmailUtil.class);

    public static void send(EmailConfig config, EmailModel email) {
        LOGGER.info("Send email with config {} and email {}", JsonConverter.toJson(config), JsonConverter.toJson(email));
        try {
            Session session = setupEmailServer(config);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getFrom()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getSendTo()));
            message.setSubject(email.getTitle());
            message.setContent(email.getContent(), "text/html; charset=utf-8");

            LOGGER.debug("Sending email...");
            // Send message
            Transport.send(message);
            LOGGER.debug("Sent email successfully.");
        } catch (MessagingException e) {
            LOGGER.error("Send email failed caused of {}", e.getMessage());
            throw new CommonException.EmailException(e.getMessage());
        }
    }

    private static Session setupEmailServer(EmailConfig emailConfig) {
        LOGGER.info("Setup email server");
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", emailConfig.getHost());
        properties.put("mail.smtp.port", emailConfig.getPort());
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object.
        // and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
            }
        });
        // Used to debug SMTP issues
        session.setDebug(emailConfig.isDebug());
        return session;
    }
}
