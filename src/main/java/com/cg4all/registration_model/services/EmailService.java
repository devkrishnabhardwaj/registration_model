package com.cg4all.registration_model.services;

public interface EmailService {

    //
    int sendOtpEmail(String to);

    //
    void sendEmailWithHtml();

    //
    void sendEmailWithAttachment();

}
