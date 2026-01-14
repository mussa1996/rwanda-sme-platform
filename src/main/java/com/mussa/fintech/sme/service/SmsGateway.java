package com.mussa.fintech.sme.service;

public interface SmsGateway {

    /**
     * Sends SMS and returns provider message ID
     */
    String send(String recipient, String message);
}
