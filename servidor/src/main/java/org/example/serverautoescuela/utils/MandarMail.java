package org.example.serverautoescuela.utils;

import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.serverautoescuela.config.Configuration;

import java.util.Properties;

public class MandarMail {
    private final Configuration config;

    @Inject
    public MandarMail(Configuration config) {
        this.config = config;
    }

    public void generateAndSendEmail(String to, String msg, String subject) throws MessagingException {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;

        // Step1
        mailServerProperties = System.getProperties();
        mailServerProperties.put(Constantes.MAIL_SMTP_PORT, config.getHost());
        mailServerProperties.put(Constantes.MAIL_SMTP_PORT, Integer.parseInt(Constantes.VALUE_MAIL_SMT_PORT));
        mailServerProperties.put(Constantes.MAIL_SMTP_AUTH, Constantes.VALUE_MAIL_SMTP_AUTH);
        mailServerProperties.put(Constantes.MAIL_SMTP_SSL_TRUST, config.getHost());
        mailServerProperties.put(Constantes.MAIL_SMTP_STARTTLS_ENABLE, Constantes.VALUE_MAIL_SMTP_AUTH);
        mailServerProperties.put(Constantes.MAIL_SMTP_USER, config.getUser_email());

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.setFrom(config.getUser_email());
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);

        generateMailMessage.setContent(msg, Constantes.TEXT_HTML);
        Transport transport = getMailSession.getTransport(Constantes.SMTP);
        transport.connect(config.getHost(),
                config.getUser_email(),
                config.getPassword_email());
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
