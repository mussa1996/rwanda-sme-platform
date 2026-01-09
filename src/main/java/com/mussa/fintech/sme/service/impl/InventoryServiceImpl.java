package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.inventory.RestockRequest;
import com.mussa.fintech.sme.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    @Override
    public void restock(RestockRequest request) {
        // update inventory balance
        // create inventory movement
    }

    @Override
    public void deductStock(UUID productId, int quantity) {
        // lock inventory
        // deduct
        // validate no negative stock
    }
}
