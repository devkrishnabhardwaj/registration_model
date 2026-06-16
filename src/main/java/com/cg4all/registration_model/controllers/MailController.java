package com.cg4all.registration_model.controllers;

import com.cg4all.registration_model.services.EmailService;
import com.cg4all.registration_model.services.impl.EmailServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    EmailService emailService;

    @GetMapping("/send-otp-mail")
    public void sendMail(HttpSession session){

        int otp = emailService.sendOtpEmail("240231028@hbtu.ac.in");
        session.setAttribute("otp",String.valueOf(otp));
    }

}
