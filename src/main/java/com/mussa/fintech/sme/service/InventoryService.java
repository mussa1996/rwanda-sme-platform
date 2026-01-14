package com.mussa.fintech.sme.service;

import com.mussa.fintech.sme.dto.inventory.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.UUID;

public interface InventoryService {

    InventoryBalanceResponse getBalance(UUID merchantId, UUID productId);

    void restock(
            UUID merchantId,
            RestockRequest request
    );

    void deductStock(
            UUID merchantId,
            UUID productId,
            BigDecimal quantity,
            String referenceType,
            UUID referenceId
    );

    Page<InventoryMovementResponse> listMovements(
            UUID merchantId,
            int page,
            int size
    );
}
