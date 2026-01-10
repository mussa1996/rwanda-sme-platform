package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.ApiResponse;
import com.mussa.fintech.sme.dto.payments.AggregatorWebhookPayload;
import com.mussa.fintech.sme.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments/webhook")
public class PaymentWebhookController {

    private final PaymentService paymentService;

    public PaymentWebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> receiveWebhook(@RequestBody AggregatorWebhookPayload payload) {
        paymentService.processWebhook(payload);
        return ResponseEntity.ok(ApiResponse.success("Webhook processed"));
    }
}
