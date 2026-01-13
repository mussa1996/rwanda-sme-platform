package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.entity.merchants.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, UUID> {

    Optional<MerchantUser> findByMerchant_MerchantIdAndPhoneNumber(
            UUID merchantId,
            String phoneNumber
    );

    Optional<MerchantUser> findByPhoneNumber(String phoneNumber);
    Optional<MerchantUser> findByPhoneNumberAndIsActiveTrue(String phoneNumber);
}
