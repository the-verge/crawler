package com.verge.crawler.email;

import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailUtils {

    public static void sendNotification() {
        JavaMailSender sender = getJavaMailSender();
        SimpleMailMessage message = getMessage();
        sender.send(message);
    }

    private static JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("");
        mailSender.setPort(587);

        mailSender.setUsername("");
        mailSender.setPassword("");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    private static SimpleMailMessage getMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("@gmail.com");
        message.setSubject("Sony WH-1000x in stock");
        message.setText("Sony WH-1000x in stock");

        return message;
    }
}
