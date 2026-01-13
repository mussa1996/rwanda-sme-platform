package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.common.enums.QrType;
import com.mussa.fintech.sme.dto.merchants.qr.CreateMerchantQrRequest;
import com.mussa.fintech.sme.dto.merchants.qr.MerchantQrResponse;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import com.mussa.fintech.sme.entity.merchants.MerchantQrCode;
import com.mussa.fintech.sme.exception.ConflictException;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.MerchantQrCodeRepository;
import com.mussa.fintech.sme.repository.MerchantRepository;
import com.mussa.fintech.sme.service.MerchantQrService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MerchantQrServiceImpl implements MerchantQrService {

    private final MerchantRepository merchantRepository;
    private final MerchantQrCodeRepository merchantQrCodeRepository;

    @Override
    public MerchantQrResponse generateQr(UUID merchantId, CreateMerchantQrRequest request) {
        Merchant merchant = findMerchant(merchantId);

        String payload = generatePayload(merchant.getMerchantId(), request.getQrType());

        // (rare) ensure payload uniqueness
        if (merchantQrCodeRepository.existsByQrPayload(payload)) {
            throw new ConflictException("Generated QR payload already exists, try again");
        }

        MerchantQrCode qr = MerchantQrCode.builder()
                .merchant(merchant)
                .qrType(request.getQrType())
                .qrPayload(payload)
                .isActive(false) // created but not active by default
                .build();

        merchantQrCodeRepository.save(qr);

        return map(qr);
    }

    @Override
    public MerchantQrResponse activateQr(UUID merchantId, UUID merchantQrId) {
        MerchantQrCode qr = merchantQrCodeRepository
                .findByMerchantQrIdAndMerchant_MerchantId(merchantQrId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("QR not found for this merchant"));

        // Business rule: allow only one active QR per type (recommended)
        List<MerchantQrCode> sameType = merchantQrCodeRepository.findAllByMerchant_MerchantIdAndQrType(merchantId, qr.getQrType());
        for (MerchantQrCode other : sameType) {
            if (!other.getMerchantQrId().equals(qr.getMerchantQrId()) && other.isActive()) {
                other.setActive(false);
            }
        }

        qr.setActive(true);
        return map(qr);
    }

    @Override
    public MerchantQrResponse deactivateQr(UUID merchantId, UUID merchantQrId) {
        MerchantQrCode qr = merchantQrCodeRepository
                .findByMerchantQrIdAndMerchant_MerchantId(merchantQrId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("QR not found for this merchant"));

        qr.setActive(false);
        return map(qr);
    }

    @Override
    public List<MerchantQrResponse> listMerchantQrs(UUID merchantId) {
        // ensure merchant exists (cleaner error)
        findMerchant(merchantId);

        return merchantQrCodeRepository.findAllByMerchant_MerchantIdOrderByCreatedAtDesc(merchantId)
                .stream()
                .map(this::map)
                .toList();
    }

    private Merchant findMerchant(UUID merchantId) {
        return merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found: " + merchantId));
    }

    private String generatePayload(UUID merchantId, QrType qrType) {
        // You can later replace this with EMVCo / MoMo payload formatting.
        // STATIC: stable payload for printing
        // DYNAMIC: includes time + nonce (good for dynamic payments)
        if (qrType == QrType.STATIC) {
            return "RW-SME:STATIC:" + merchantId;
        }
        String nonce = UUID.randomUUID().toString().replace("-", "");
        return "RW-SME:DYNAMIC:" + merchantId + ":" + OffsetDateTime.now().toEpochSecond() + ":" + nonce;
    }

    private MerchantQrResponse map(MerchantQrCode qr) {
        return new MerchantQrResponse(
                qr.getMerchantQrId(),
                qr.getMerchant().getMerchantId(),
                qr.getQrType().name(),
                qr.getQrPayload(),
                qr.isActive()
        );
    }
}
