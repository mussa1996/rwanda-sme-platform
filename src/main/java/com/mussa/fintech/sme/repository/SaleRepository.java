package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.entity.sales.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {

    List<Sale> findByMerchant_MerchantIdOrderByCreatedAtDesc(UUID merchantId);

    @Query("""
        SELECT s FROM Sale s
        WHERE s.merchant.merchantId = :merchantId
          AND s.totalAmount = :amount
          AND s.status = 'PENDING'
          AND s.createdAt >= :fromTime
        ORDER BY s.createdAt DESC
    """)
    Optional<Sale> findMatchingPendingSale(
            UUID merchantId,
            BigDecimal amount,
            OffsetDateTime fromTime
    );
}
