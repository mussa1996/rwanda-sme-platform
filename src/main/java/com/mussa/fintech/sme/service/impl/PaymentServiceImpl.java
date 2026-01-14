package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.common.enums.PaymentStatus;
import com.mussa.fintech.sme.common.enums.SaleStatus;
import com.mussa.fintech.sme.dto.payments.AggregatorWebhookPayload;
import com.mussa.fintech.sme.dto.payments.PaymentResponse;
import com.mussa.fintech.sme.entity.payments.Payment;
import com.mussa.fintech.sme.entity.payments.PaymentWebhookEvent;
import com.mussa.fintech.sme.entity.sales.Sale;
import com.mussa.fintech.sme.exception.ConflictException;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.*;
import com.mussa.fintech.sme.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentWebhookEventRepository webhookEventRepository;
    private final SaleRepository saleRepository;

    /* ---------------- Webhook Processing ---------------- */

    @Override
    public void processWebhook(
            AggregatorWebhookPayload payload,
            boolean signatureValid,
            String rawPayload
    ) {
        // 1️⃣ Persist webhook event (ALWAYS)
        webhookEventRepository.save(
                PaymentWebhookEvent.builder()
                        .provider(payload.getProvider())
                        .eventType(payload.getStatus())
                        .providerTxnId(payload.getTransactionId())
                        .signatureValid(signatureValid)
                        .payloadJson(rawPayload)
                        .receivedAt(OffsetDateTime.now())
                        .build()
        );

        // 2️⃣ Idempotency
        if (paymentRepository.existsByProviderAndProviderTxnId(
                payload.getProvider(), payload.getTransactionId())) {
            log.warn("Duplicate webhook received for txn {}", payload.getTransactionId());
            return; // safe ignore
        }

        // 3️⃣ Locate sale
        Sale sale = saleRepository.findById(
                        UUID.fromString(payload.getMerchantReference()))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sale not found"));

        // 4️⃣ Validate sale state
        if (sale.getStatus() != SaleStatus.PENDING) {
            throw new ConflictException("Sale is not in PENDING state");
        }

        // 5️⃣ Map payment status
        PaymentStatus paymentStatus = mapStatus(payload.getStatus());

        // 6️⃣ Save payment
        Payment payment = Payment.builder()
                .sale(sale)
                .merchant(sale.getMerchant())
                .provider(payload.getProvider())
                .network(payload.getNetwork())
                .providerTxnId(payload.getTransactionId())
                .amount(payload.getAmount())
                .currency(payload.getCurrency())
                .status(paymentStatus)
                .paidAt(paymentStatus == PaymentStatus.SUCCESS
                        ? OffsetDateTime.now()
                        : null)
                .build();

        paymentRepository.save(payment);

        // 7️⃣ Update sale status
        if (paymentStatus == PaymentStatus.SUCCESS) {
            sale.setStatus(SaleStatus.PAID);
        } else if (paymentStatus == PaymentStatus.FAILED) {
            sale.setStatus(SaleStatus.FAILED);
        }

        log.info("Payment processed for sale {}", sale.getSaleId());
    }

    /* ---------------- List Payments ---------------- */

    @Override
    public Page<PaymentResponse> listPayments(
            UUID merchantId,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return paymentRepository.findAllByMerchant_MerchantId(merchantId, pageable)
                .map(p -> new PaymentResponse(
                        p.getPaymentId(),
                        p.getSale() != null ? p.getSale().getSaleId() : null,
                        p.getProvider(),
                        p.getProviderTxnId(),
                        p.getAmount(),
                        p.getCurrency(),
                        p.getStatus().name(),
                        p.getPaidAt()
                ));
    }

    /* ---------------- Helpers ---------------- */

    private PaymentStatus mapStatus(String providerStatus) {
        return switch (providerStatus.toUpperCase()) {
            case "SUCCESS", "COMPLETED", "PAID" -> PaymentStatus.SUCCESS;
            case "FAILED", "REJECTED" -> PaymentStatus.FAILED;
            case "PENDING" -> PaymentStatus.PENDING;
            default -> PaymentStatus.UNKNOWN;
        };
    }
}
