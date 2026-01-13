package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.common.enums.MerchantStatus;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

    Optional<Merchant> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
    @Query("""
        SELECT m FROM Merchant m
        WHERE (:status IS NULL OR m.status = :status)
          AND (
                :q IS NULL OR :q = '' OR
                LOWER(m.businessName) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(m.phoneNumber) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(COALESCE(m.email,'')) LIKE LOWER(CONCAT('%', :q, '%'))
          )
        """)
    Page<Merchant> search(
            @Param("status") MerchantStatus status,
            @Param("q") String q,
            Pageable pageable
    );
}
