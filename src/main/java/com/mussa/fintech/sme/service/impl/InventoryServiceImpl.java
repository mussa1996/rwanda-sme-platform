package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.common.enums.InventoryMovementType;
import com.mussa.fintech.sme.dto.inventory.*;
import com.mussa.fintech.sme.entity.inventory.InventoryBalance;
import com.mussa.fintech.sme.entity.inventory.InventoryMovement;
import com.mussa.fintech.sme.entity.products.Product;
import com.mussa.fintech.sme.exception.InventoryException;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.*;
import com.mussa.fintech.sme.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryBalanceRepository balanceRepository;
    private final InventoryMovementRepository movementRepository;
    private final ProductRepository productRepository;

    /* ---------------- Get Balance ---------------- */

    @Override
    public InventoryBalanceResponse getBalance(UUID merchantId, UUID productId) {

        InventoryBalance balance = balanceRepository.findByProduct_ProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        Product product = balance.getProduct();

        if (!product.getMerchant().getMerchantId().equals(merchantId)) {
            throw new ResourceNotFoundException("Product does not belong to this merchant");
        }

        return new InventoryBalanceResponse(
                product.getProductId(),
                product.getProductName(),
                balance.getQuantityOnHand()
        );
    }

    /* ---------------- Restock ---------------- */

    @Override
    public void restock(UUID merchantId, RestockRequest req) {

        InventoryBalance balance = balanceRepository.lockByProductId(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        Product product = balance.getProduct();

        if (!product.getMerchant().getMerchantId().equals(merchantId)) {
            throw new ResourceNotFoundException("Product does not belong to this merchant");
        }

        balance.setQuantityOnHand(
                balance.getQuantityOnHand().add(req.getQuantity())
        );
        balance.setLastUpdatedAt(OffsetDateTime.now());

        movementRepository.save(
                InventoryMovement.builder()
                        .merchant(product.getMerchant())
                        .product(product)
                        .movementType(InventoryMovementType.RESTOCK)
                        .quantityChange(req.getQuantity())
                        .referenceType("MANUAL_RESTOCK")
                        .referenceId(UUID.randomUUID())
                        .build()
        );

        log.info("Restocked product {} by {}", product.getProductId(), req.getQuantity());
    }

    /* ---------------- Deduct (Sales, Adjustments) ---------------- */

    @Override
    public void deductStock(
            UUID merchantId,
            UUID productId,
            BigDecimal quantity,
            String referenceType,
            UUID referenceId
    ) {
        InventoryBalance balance = balanceRepository.lockByProductId(productId)
                .orElseThrow(() -> new InventoryException("Inventory not found"));

        Product product = balance.getProduct();

        if (!product.getMerchant().getMerchantId().equals(merchantId)) {
            throw new ResourceNotFoundException("Product does not belong to this merchant");
        }

        if (balance.getQuantityOnHand().compareTo(quantity) < 0) {
            throw new InventoryException("Insufficient stock");
        }

        balance.setQuantityOnHand(
                balance.getQuantityOnHand().subtract(quantity)
        );
        balance.setLastUpdatedAt(OffsetDateTime.now());

        movementRepository.save(
                InventoryMovement.builder()
                        .merchant(product.getMerchant())
                        .product(product)
                        .movementType(InventoryMovementType.SALE)
                        .quantityChange(quantity.negate())
                        .referenceType(referenceType)
                        .referenceId(referenceId)
                        .build()
        );

        log.info("Deducted {} from product {}", quantity, productId);
    }

    /* ---------------- Movement History ---------------- */

    @Override
    public Page<InventoryMovementResponse> listMovements(
            UUID merchantId,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), 100),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return movementRepository.findAllByMerchant_MerchantId(merchantId, pageable)
                .map(m -> new InventoryMovementResponse(
                        m.getInventoryMovementId(),
                        m.getProduct().getProductId(),
                        m.getProduct().getProductName(),
                        m.getMovementType().name(),
                        m.getQuantityChange(),
                        m.getReferenceType(),
                        m.getReferenceId(),
                        m.getCreatedAt()
                ));
    }
}
