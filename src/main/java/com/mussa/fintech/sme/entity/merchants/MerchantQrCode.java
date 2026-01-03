package com.mussa.fintech.sme.entity.merchants;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "merchant_qr_codes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_qr_payload", columnNames = "qr_payload")
        }
)
public class MerchantQrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "merchant_qr_id")
    private UUID merchantQrId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false)
    private String qrType;

    @Column(name = "qr_payload", nullable = false)
    private String qrPayload;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
}

