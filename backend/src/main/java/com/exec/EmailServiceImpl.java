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
        template_emails.put( "Campign Request", "Hey %s,\n\n"
                + "You have been requested to be a campaigner for %s\n"
                + "Please log into your Exec account to accept or reject the request\n\n"
                + "Thank you,\n"
                + "Exec Team");
        template_emails.put("Campaigner Acceptance", "Hey %s,\n\n"
                + "%s has accepted your campaigning request\n"
                + "Please log into your account to view the changes\n\n"
                + "Thank you,\n"
                + "Exec Team");
        template_emails.put("Campaigner Rejection", "Hey %s,\n\n"
                + "%s has rejected your campaigning request\n"
                + "Please log into your account to view the changes\n\n"
                + "Thank you,\n"
                + "Exec Team");

        template_subjects.put( "OTP", "Exec - Sign Up OTP");
        template_subjects.put( "Campign Request", "Exec - Campaign Request");
        template_subjects.put( "Campaigner Acceptance", "Exec - Campaigner Acceptance");
        template_subjects.put( "Campaigner Rejection", "Exec - Campaigner Rejection");
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

    public void sendCampaignRequestMessage(String to, String campaigner, String campaignee) {
        String text = String.format(template_emails.get("Campign Request"), campaigner, campaignee);
        String subject = template_subjects.get("Campign Request");
        sendSimpleMessage(to, subject, text);
    }

    public void sendCampaignerAcceptanceMessage(String to, String campaigner, String campaignee) {
        String text = String.format(template_emails.get("Campaigner Acceptance"), campaignee, campaigner);
        String subject = template_subjects.get("Campaigner Acceptance");
        sendSimpleMessage(to, subject, text);
    }
    
    public void sendCampaignerRejectionMessage(String to, String campaigner, String campaignee) {
        String text = String.format(template_emails.get("Campaigner Rejection"), campaignee, campaigner);
        String subject = template_subjects.get("Campaigner Rejection");
        sendSimpleMessage(to, subject, text);
    }
}
