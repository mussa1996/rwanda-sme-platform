package com.mussa.fintech.sme.entity.merchants;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "merchant_users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_merchant_user_phone", columnNames = {"merchant_id", "phone_number"})
        }
)
public class MerchantUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "merchant_user_id", nullable = false)
    private UUID merchantUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false)
    private String fullName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    private String email;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;
}

