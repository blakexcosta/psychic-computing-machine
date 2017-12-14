//import Model.*;
import Model.MySQLDatabase;
import View.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

/**
 * Main method class, used to instantiate whole fleet of programs/ classes.
 */
public class Instantiation {
    public static void main(String[] args) {
        //test login
        //ab1234 (al baker)
        //5f4dcc3b5aa765d61d8327deb882cf99
        //runs the program
        //| GradCoord | Qi        | Yu          | Password | https://ist.rit.edu/assets/img/people/qyuvks.jpg                                       | 1900-01-01     | Associate Professor          |       | staff   |
        //creates a new thread to run the javafx application, goes to MasterView

        //gonna test sending an email here first, will make a new commit once this runs
        String username = "armykids117@gmail.com";
        String password = "c00ldud310";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        //properties.setProperty("smtp.gmail.com", host);
        //Session session = Session.getDefaultInstance(properties);
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("wutangwebsolutions@gmail.com"));
            //setting where you want to send it to
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("armykids117@gmail.com"));
            message.setSubject("Testing Mail Functionality");
            message.setText("TO whomever it concerns, this is a test email\n" +
                    "for a school class project");
            Transport.send(message);
            //            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // Set Subject: header field
//            message.setSubject("This is the Subject Line!");
//
//            // Now set the actual message
//            message.setText("This is actual message");
//
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

        new Thread() {
            @Override
            public void run(){
                javafx.application.Application.launch(MasterView.class);
            }
        }.start();
    }
}
