package com.mussa.fintech.sme.entity.sales;

import com.mussa.fintech.sme.common.AuditableEntity;
import com.mussa.fintech.sme.common.enums.SaleChannel;
import com.mussa.fintech.sme.common.enums.SaleStatus;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import com.mussa.fintech.sme.entity.merchants.MerchantUser;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "sales",
        indexes = {
                @Index(name = "idx_sales_merchant", columnList = "merchant_id"),
                @Index(name = "idx_sales_status", columnList = "status")
        }
)
public class Sale extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sale_id")
    private UUID saleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sold_by_user_id")
    private MerchantUser soldByUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleChannel saleChannel;

    @Column(nullable = false)
    private BigDecimal subtotalAmount;

    private BigDecimal discountAmount;
    private BigDecimal taxAmount;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus status;
    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SaleItem> items = new ArrayList<>();
}

