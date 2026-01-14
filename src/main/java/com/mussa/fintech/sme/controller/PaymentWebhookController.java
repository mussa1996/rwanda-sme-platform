package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.ApiResponse;
import com.mussa.fintech.sme.dto.payments.AggregatorWebhookPayload;
import com.mussa.fintech.sme.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/payments/webhook")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse> receiveWebhook(
            @Valid @RequestBody AggregatorWebhookPayload payload,
            HttpServletRequest request
    ) throws IOException {

        // 🔐 Placeholder for signature verification
        boolean signatureValid = true;

        // Raw payload for audit
        String rawPayload = request.getReader().lines()
                .reduce("", (a, b) -> a + b);

        paymentService.processWebhook(payload, signatureValid, rawPayload);

        return ResponseEntity.ok(
                ApiResponse.success("Webhook processed successfully")
        );
    }
}
