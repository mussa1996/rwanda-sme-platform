package com.mussa.fintech.sme.entity.inventory;

import com.mussa.fintech.sme.common.AuditableEntity;
import com.mussa.fintech.sme.common.enums.InventoryMovementType;
import com.mussa.fintech.sme.entity.merchants.Merchant;
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
        name = "inventory_movements",
        indexes = {
                @Index(name = "idx_inventory_movements_merchant", columnList = "merchant_id"),
                @Index(name = "idx_inventory_movements_product", columnList = "product_id")
        }
)
public class InventoryMovement extends AuditableEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryMovementType movementType;

    @Column(nullable = false)
    private BigDecimal quantityChange;

    @Column(nullable = false)
    private String referenceType;

    @Column(nullable = false)
    private UUID referenceId;
}

