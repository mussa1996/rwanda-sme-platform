package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.common.enums.PaymentStatus;
import com.mussa.fintech.sme.common.enums.SaleStatus;
import com.mussa.fintech.sme.dto.payments.AggregatorWebhookPayload;
import com.mussa.fintech.sme.entity.payments.Payment;
import com.mussa.fintech.sme.entity.sales.Sale;
import com.mussa.fintech.sme.exception.ConflictException;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.PaymentRepository;
import com.mussa.fintech.sme.repository.SaleRepository;
import com.mussa.fintech.sme.service.InventoryService;
import com.mussa.fintech.sme.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepo;
    private final SaleRepository saleRepo;
    private final InventoryService inventoryService;

    @Override
    public void processWebhook(AggregatorWebhookPayload payload) {

        if (paymentRepo.existsByProviderAndProviderTxnId(
                payload.getProvider(), payload.getTransactionId())) {
            throw new ConflictException("Duplicate payment");
        }

        Sale sale = saleRepo.findById(UUID.fromString(payload.getMerchantReference()))
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        sale.setStatus(SaleStatus.PAID);

        paymentRepo.save(
                Payment.builder()
                        .sale(sale)
                        .provider(payload.getProvider())
                        .providerTxnId(payload.getTransactionId())
                        .amount(payload.getAmount())
                        .status(PaymentStatus.SUCCESS)
                        .build()
        );

        sale.getItems().forEach(item ->
                inventoryService.deductStock(
                        item.getProduct().getProductId(),
                        item.getQuantity().intValue()
                )
        );
    }
}

