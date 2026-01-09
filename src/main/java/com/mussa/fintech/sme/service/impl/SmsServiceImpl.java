package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.service.SmsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SmsServiceImpl implements SmsService {

    @Override
    public void sendSms(String phoneNumber, String message) {
        // integrate SMS provider later
    }
}
