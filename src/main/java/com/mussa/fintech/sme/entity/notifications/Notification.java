package com.mussa.fintech.sme.entity.notifications;

import com.mussa.fintech.sme.entity.merchants.Merchant;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "notifications",
        indexes = {
                @Index(name = "idx_notifications_merchant", columnList = "merchant_id"),
                @Index(name = "idx_notifications_status", columnList = "status")
        }
)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "notification_id")
    private UUID notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @Column(nullable = false)
    private String channel;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String messageTemplate;

    @Column(nullable = false)
    private String messageContent;

    @Column(nullable = false)
    private String status;

    private String providerMessageId;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime sentAt;
}

