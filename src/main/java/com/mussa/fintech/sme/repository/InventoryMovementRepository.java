package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.entity.inventory.InventoryMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InventoryMovementRepository
        extends JpaRepository<InventoryMovement, UUID> {

    List<InventoryMovement> findByMerchant_MerchantIdOrderByCreatedAtDesc(UUID merchantId);
    Page<InventoryMovement> findAllByMerchant_MerchantId(
            UUID merchantId, Pageable pageable
    );
}
