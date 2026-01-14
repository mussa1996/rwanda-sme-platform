package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.entity.payments.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    boolean existsByProviderAndProviderTxnId(String provider, String providerTxnId);

    Optional<Payment> findByProviderAndProviderTxnId(
            String provider,
            String providerTxnId
    );
    Page<Payment> findAllByMerchant_MerchantId(UUID merchantId, Pageable pageable);
}
