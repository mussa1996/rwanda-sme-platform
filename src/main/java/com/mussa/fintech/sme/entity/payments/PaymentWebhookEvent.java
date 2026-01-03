package com.mussa.fintech.sme.entity.payments;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "payment_webhook_events",
        indexes = {
                @Index(name = "idx_webhook_provider_txn", columnList = "provider, provider_txn_id")
        }
)
public class PaymentWebhookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "webhook_event_id")
    private UUID webhookEventId;

    @Column(nullable = false)
    private String provider;

    private String eventType;

    @Column(name = "provider_txn_id")
    private String providerTxnId;

    @Column(nullable = false)
    private boolean signatureValid;

    @Column(columnDefinition = "jsonb", nullable = false)
    private String payloadJson;

    @Column(nullable = false)
    private OffsetDateTime receivedAt;
}

