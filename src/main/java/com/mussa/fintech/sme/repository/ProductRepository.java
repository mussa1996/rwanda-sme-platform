package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.entity.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByMerchant_MerchantIdAndIsActiveTrue(UUID merchantId);
    boolean existsByMerchant_MerchantIdAndProductNameIgnoreCase(
            UUID merchantId, String productName
    );

    Page<Product> findAllByMerchant_MerchantId(
            UUID merchantId, Pageable pageable
    );

    @Query("""
        SELECT p FROM Product p
        WHERE p.merchant.merchantId = :merchantId
          AND (:active IS NULL OR p.isActive = :active)
          AND (
                :q IS NULL OR :q = '' OR
                LOWER(p.productName) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(COALESCE(p.sku,'')) LIKE LOWER(CONCAT('%', :q, '%'))
          )
    """)
    Page<Product> search(
            @Param("merchantId") UUID merchantId,
            @Param("active") Boolean active,
            @Param("q") String q,
            Pageable pageable
    );
}
