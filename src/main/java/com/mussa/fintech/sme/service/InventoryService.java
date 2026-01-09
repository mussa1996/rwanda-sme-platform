package com.mussa.fintech.sme.service;


import com.mussa.fintech.sme.dto.inventory.RestockRequest;

import java.util.UUID;

public interface InventoryService {

    void restock(RestockRequest request);

    void deductStock(UUID productId, int quantity);
}
