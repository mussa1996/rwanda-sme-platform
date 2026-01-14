package com.mussa.fintech.sme.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class InventoryMovementResponse {

    private UUID movementId;
    private UUID productId;
    private String productName;
    private String movementType;
    private BigDecimal quantityChange;
    private String referenceType;
    private UUID referenceId;
    private OffsetDateTime createdAt;
}
