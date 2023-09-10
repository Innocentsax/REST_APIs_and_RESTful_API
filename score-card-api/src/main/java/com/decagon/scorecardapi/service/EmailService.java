package com.decagon.scorecardapi.service;



public interface EmailService {


    boolean sendEmail(String body, String subject, String email);
}

