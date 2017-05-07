package controllers;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class Email {

    //public static void main(String[] args) {

    //    String mailSmtpHost = "localhost";

    //    String mailTo = "hanna.storaker@gmail.com";
    //    String mailFrom = "me@here.there.everywhere";
    //    String mailSubject = "Email from Java";
    //    String mailText = "Helluu babe";

    //    sendEmail(mailTo, mailFrom, mailSubject, mailText, mailSmtpHost);
    //q}

    public static void sendEmail(String to, String from, String subject, String text, String smtpHost) {
        try {
            Properties properties = new Properties();;
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.user", "username"); // User name
            properties.put("mail.smtp.password", "password"); // password
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            //Session emailSession = Session.getDefaultInstance(properties);
            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    "digitalbibliographiclibrary@gmail.com", "kjeks123");// Specify the Username and the PassWord
                        }
                    });
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(text, "text/html");
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mbp);


            MimeMessage emailMessage = new MimeMessage(session);

            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            emailMessage.setFrom(new InternetAddress(from));
            emailMessage.setContent(multipart);
            emailMessage.setSubject(subject);
            emailMessage.setText(text, "UTF-8", "html");
            session.setDebug(true);

            Transport.send(emailMessage);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}