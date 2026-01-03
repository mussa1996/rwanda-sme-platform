package com.mussa.fintech.sme.entity.inventory;

import com.mussa.fintech.sme.entity.merchants.Merchant;
import com.mussa.fintech.sme.entity.products.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "inventory_movements",
        indexes = {
                @Index(name = "idx_inventory_movements_merchant", columnList = "merchant_id"),
                @Index(name = "idx_inventory_movements_product", columnList = "product_id")
        }
)
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "inventory_movement_id")
    private UUID inventoryMovementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String movementType;

    @Column(nullable = false)
    private BigDecimal quantityChange;

    @Column(nullable = false)
    private String referenceType;

    @Column(nullable = false)
    private UUID referenceId;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
}

