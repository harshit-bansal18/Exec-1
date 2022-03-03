package com.exec;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl extends EmailSettings {

    @Lazy
    @Autowired
    private JavaMailSender emailSender = getJavaMailSender();
    private Map<String, String> template_emails = new HashMap<String, String>();
    private Map<String, String> template_subjects = new HashMap<String, String>();

    public EmailServiceImpl() {
        template_emails.put( "OTP", "Hey %s,\n\n"
                + "Please enter the following otp to complete the sign up process\n"
                + "OTP: %s\n\n"
                + "Thank you,\n"
                + "Exec Team");
        template_subjects.put( "OTP", "Exec - Sign Up OTP");
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }

    public void sendOTPMessage(String to, String name, String otp) {
        String text = String.format(template_emails.get("OTP"), name, otp);
        String subject = template_subjects.get("OTP");
        sendSimpleMessage(to, subject, text);
    }
}
