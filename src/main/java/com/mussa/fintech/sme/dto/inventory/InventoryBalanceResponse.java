package com.mussa.fintech.sme.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class InventoryBalanceResponse {

    private UUID productId;
    private String productName;
    private BigDecimal quantityOnHand;
}
