package com.mussa.fintech.sme.entity.inventory;

import com.mussa.fintech.sme.common.AuditableEntity;
import com.mussa.fintech.sme.entity.products.Product;
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
        name = "inventory_balances",
        indexes = {
                @Index(name = "idx_inventory_product", columnList = "product_id")
        }
)
public class InventoryBalance extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "inventory_balance_id")
    private UUID inventoryBalanceId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal quantityOnHand;

    @Column(nullable = false)
    private OffsetDateTime lastUpdatedAt;
}

