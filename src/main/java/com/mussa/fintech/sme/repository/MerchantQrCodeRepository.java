package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.entity.merchants.MerchantQrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MerchantQrCodeRepository extends JpaRepository<MerchantQrCode, UUID> {

    Optional<MerchantQrCode> findByQrPayloadAndIsActiveTrue(String qrPayload);

    Optional<MerchantQrCode> findByMerchant_MerchantIdAndIsActiveTrue(UUID merchantId);
}
