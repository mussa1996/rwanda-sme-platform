package com.mussa.fintech.sme.service;

import com.mussa.fintech.sme.dto.payments.AggregatorWebhookPayload;
import com.mussa.fintech.sme.dto.payments.PaymentResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PaymentService {

    void processWebhook(
            AggregatorWebhookPayload payload,
            boolean signatureValid,
            String rawPayload
    );

    Page<PaymentResponse> listPayments(
            UUID merchantId,
            int page,
            int size
    );
}
