package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.entity.payments.PaymentWebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentWebhookEventRepository
        extends JpaRepository<PaymentWebhookEvent, UUID> {
}
