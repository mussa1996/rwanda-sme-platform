package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.common.enums.InventoryMovementType;
import com.mussa.fintech.sme.dto.inventory.RestockRequest;
import com.mussa.fintech.sme.entity.inventory.InventoryBalance;
import com.mussa.fintech.sme.entity.inventory.InventoryMovement;
import com.mussa.fintech.sme.exception.InventoryException;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.InventoryBalanceRepository;
import com.mussa.fintech.sme.repository.InventoryMovementRepository;
import com.mussa.fintech.sme.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryBalanceRepository balanceRepo;
    private final InventoryMovementRepository movementRepo;

    @Override
    public void restock(RestockRequest req) {

        InventoryBalance balance = balanceRepo
                .lockByProductId(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        balance.setQuantityOnHand(
                balance.getQuantityOnHand().add(req.getQuantity())
        );

        movementRepo.save(
                InventoryMovement.builder()
                        .product(balance.getProduct())
                        .movementType(InventoryMovementType.RESTOCK)
                        .quantityChange(req.getQuantity())
                        .build()
        );
    }

    @Override
    public void deductStock(UUID productId, int quantity) {

        InventoryBalance balance = balanceRepo
                .lockByProductId(productId)
                .orElseThrow(() -> new InventoryException("Inventory not found"));

        if (balance.getQuantityOnHand().intValue() < quantity) {
            throw new InventoryException("Insufficient stock");
        }

        balance.setQuantityOnHand(
                balance.getQuantityOnHand().subtract(BigDecimal.valueOf(quantity))
        );

        movementRepo.save(
                InventoryMovement.builder()
                        .product(balance.getProduct())
                        .movementType(InventoryMovementType.SALE)
                        .quantityChange(BigDecimal.valueOf(-quantity))
                        .build()
        );
    }
}

