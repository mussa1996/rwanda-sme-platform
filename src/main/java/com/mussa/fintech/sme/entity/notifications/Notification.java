package com.mussa.fintech.sme.entity.notifications;

import com.mussa.fintech.sme.common.AuditableEntity;
import com.mussa.fintech.sme.common.enums.NotificationChannel;
import com.mussa.fintech.sme.common.enums.NotificationStatus;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "notifications",
        indexes = {
                @Index(name = "idx_notifications_merchant", columnList = "merchant_id"),
                @Index(name = "idx_notifications_status", columnList = "status")
        }
)
public class Notification extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "notification_id")
    private UUID notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String messageTemplate;

    @Column(nullable = false)
    private String messageContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    private String providerMessageId;

    private OffsetDateTime sentAt;
}

