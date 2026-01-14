package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.PagedResponse;
import com.mussa.fintech.sme.dto.payments.PaymentResponse;
import com.mussa.fintech.sme.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/merchants/{merchantId}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<PagedResponse<PaymentResponse>> listPayments(
            @PathVariable UUID merchantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<PaymentResponse> result =
                paymentService.listPayments(merchantId, page, size);

        return ResponseEntity.ok(
                PagedResponse.of(
                        "Payments retrieved successfully",
                        result.getContent(),
                        result.getNumber(),
                        result.getSize(),
                        result.getTotalElements(),
                        result.getTotalPages()
                )
        );
    }
}
