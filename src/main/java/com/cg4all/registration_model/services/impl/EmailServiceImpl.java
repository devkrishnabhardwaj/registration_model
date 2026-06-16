package com.cg4all.registration_model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cg4all.registration_model.services.EmailService;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender eMailSender;

//    @Value("${spring.mail.properties.domain_name}")
//    private String domainName;

    @Override
    public int sendOtpEmail(String to) {

        String body,subject="CG4ALL Email Verification code";

        Random random = new Random();

        int otp = random.nextInt(1000,9999);

        body = otp+" is your OTP for your email verification for CG4ALL account.";
        body += "\n Do not Share with Anyone\n Regards, Team CG4ALL";


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        eMailSender.send(message);

        return otp;
    }

    @Override
    public void sendEmailWithHtml() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithHtml'");
    }

    @Override
    public void sendEmailWithAttachment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithAttachment'");
    }

}