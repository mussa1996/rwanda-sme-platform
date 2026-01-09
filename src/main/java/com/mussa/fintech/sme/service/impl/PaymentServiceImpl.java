package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.payments.AggregatorWebhookPayload;
import com.mussa.fintech.sme.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public void processWebhook(AggregatorWebhookPayload payload) {
        // idempotency check
        // update payment
        // update sale
        // trigger inventory deduction
        // trigger SMS
    }
}
