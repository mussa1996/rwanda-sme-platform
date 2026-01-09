package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.service.SmsService;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    @Override
    public void sendSms(String phoneNumber, String message) {
        // integrate SMS provider later
    }
}
