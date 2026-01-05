package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.entity.inventory.InventoryBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

public interface InventoryBalanceRepository
        extends JpaRepository<InventoryBalance, UUID> {

    Optional<InventoryBalance> findByProduct_ProductId(UUID productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT b FROM InventoryBalance b
        WHERE b.product.productId = :productId
    """)
    Optional<InventoryBalance> lockByProductId(UUID productId);
}
