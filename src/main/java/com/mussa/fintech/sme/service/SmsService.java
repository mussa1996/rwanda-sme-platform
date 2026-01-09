package com.mussa.fintech.sme.service;

public interface SmsService {

    void sendSms(String phoneNumber, String message);
}
