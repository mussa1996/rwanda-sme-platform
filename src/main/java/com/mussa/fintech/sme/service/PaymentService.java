package com.mussa.fintech.sme.service;


import com.mussa.fintech.sme.dto.payments.AggregatorWebhookPayload;

public interface PaymentService {

    void processWebhook(AggregatorWebhookPayload payload);
}
