package com.mussa.fintech.sme.entity.merchants;

import com.mussa.fintech.sme.common.AuditableEntity;
import com.mussa.fintech.sme.common.enums.QrType;
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
        name = "merchant_qr_codes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_qr_payload", columnNames = "qr_payload")
        }
)
public class MerchantQrCode extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "merchant_qr_id")
    private UUID merchantQrId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QrType qrType;

    @Column(name = "qr_payload", nullable = false)
    private String qrPayload;

    @Column(nullable = false)
    private boolean isActive;
}

