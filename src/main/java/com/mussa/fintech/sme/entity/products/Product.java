package com.mussa.fintech.sme.entity.products;

import com.mussa.fintech.sme.common.AuditableEntity;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_name_per_merchant", columnNames = {"merchant_id", "product_name"})
        }
)
public class Product extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "product_name", nullable = false)
    private String productName;

    private String sku;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private boolean isActive;
}

