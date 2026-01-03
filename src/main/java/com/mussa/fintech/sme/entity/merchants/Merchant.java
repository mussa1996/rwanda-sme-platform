package com.mussa.fintech.sme.entity.merchants;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "merchants",
        indexes = {
                @Index(name = "idx_merchants_phone", columnList = "phone_number"),
                @Index(name = "idx_merchants_status", columnList = "status")
        }
)
public class Merchant {

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

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;
}
