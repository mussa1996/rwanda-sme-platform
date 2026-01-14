package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.notifications.SmsRequest;
import com.mussa.fintech.sme.service.NotificationService;
import com.mussa.fintech.sme.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final NotificationService notificationService;

    @Override
    public void sendSms(String phoneNumber, String message) {
        SmsRequest req = new SmsRequest();
        req.setRecipient(phoneNumber);
        req.setMessage(message);

        // System notifications (no merchant context)
        notificationService.sendSms(null, req);
    }
}
