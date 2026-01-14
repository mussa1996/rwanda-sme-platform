package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.service.SmsGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class MockSmsGateway implements SmsGateway {

    @Override
    public String send(String recipient, String message) {
        log.info("[MOCK SMS] To: {}, Message: {}", recipient, message);
        return "MOCK-" + UUID.randomUUID();
    }
}

