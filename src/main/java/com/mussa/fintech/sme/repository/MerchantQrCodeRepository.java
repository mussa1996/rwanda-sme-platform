package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.common.enums.QrType;
import com.mussa.fintech.sme.entity.merchants.MerchantQrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MerchantQrCodeRepository extends JpaRepository<MerchantQrCode, UUID> {

    Optional<MerchantQrCode> findByQrPayloadAndIsActiveTrue(String qrPayload);

    Optional<MerchantQrCode> findByMerchant_MerchantIdAndIsActiveTrue(UUID merchantId);
    List<MerchantQrCode> findAllByMerchant_MerchantIdOrderByCreatedAtDesc(UUID merchantId);

    Optional<MerchantQrCode> findByMerchantQrIdAndMerchant_MerchantId(UUID qrId, UUID merchantId);

    boolean existsByQrPayload(String qrPayload);

    List<MerchantQrCode> findAllByMerchant_MerchantIdAndQrType(UUID merchantId, QrType qrType);
}
