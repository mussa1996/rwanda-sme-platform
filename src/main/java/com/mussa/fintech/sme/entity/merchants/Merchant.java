package com.mussa.fintech.sme.entity.merchants;

import com.mussa.fintech.sme.common.AuditableEntity;
import com.mussa.fintech.sme.common.enums.MerchantStatus;
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
        name = "merchants",
        indexes = {
                @Index(name = "idx_merchants_phone", columnList = "phone_number"),
                @Index(name = "idx_merchants_status", columnList = "status")
        }
)
public class Merchant extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "merchant_id", nullable = false, updatable = false)
    private UUID merchantId;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "business_category")
    private String businessCategory;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address_text")
    private String addressText;

    private String district;
    private String sector;
    private String cell;
    private String village;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantStatus status;
}
