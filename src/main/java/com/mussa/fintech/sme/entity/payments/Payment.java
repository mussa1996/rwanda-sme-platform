package com.mussa.fintech.sme.entity.payments;


import com.mussa.fintech.sme.entity.merchants.Merchant;
import com.mussa.fintech.sme.entity.sales.Sale;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "payments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_provider_txn",
                        columnNames = {"provider", "provider_txn_id"}
                )
        }
)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private UUID paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false)
    private String provider;

    private String network;

    @Column(name = "provider_txn_id", nullable = false)
    private String providerTxnId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String status;

    private OffsetDateTime paidAt;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
}
