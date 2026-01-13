package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.common.enums.MerchantStatus;
import com.mussa.fintech.sme.common.enums.UserRole;
import com.mussa.fintech.sme.dto.merchants.CreateMerchantRequest;
import com.mussa.fintech.sme.dto.merchants.MerchantResponse;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import com.mussa.fintech.sme.entity.merchants.MerchantUser;
import com.mussa.fintech.sme.exception.ConflictException;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.MerchantRepository;
import com.mussa.fintech.sme.repository.MerchantUserRepository;
import com.mussa.fintech.sme.service.MerchantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final MerchantUserRepository merchantUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MerchantResponse createMerchant(CreateMerchantRequest req) {
        if (merchantRepository.existsByPhoneNumber(req.getPhoneNumber())) {
            throw new ConflictException("Merchant with this phone number already exists");
        }

        Merchant merchant = Merchant.builder()
                .businessName(req.getBusinessName())
                .businessCategory(req.getBusinessCategory())
                .phoneNumber(req.getPhoneNumber())
                .email(req.getEmail())
                .addressText(req.getAddressText())
                .district(req.getDistrict())
                .sector(req.getSector())
                .cell(req.getCell())
                .village(req.getVillage())
                .status(MerchantStatus.ACTIVE)
                .build();

        merchantRepository.save(merchant);

        // MVP default owner user
        MerchantUser owner = MerchantUser.builder()
                .merchant(merchant)
                .fullName("Merchant Owner")
                .phoneNumber(req.getPhoneNumber())
                .email(req.getEmail())
                .role(UserRole.OWNER)
                .passwordHash(passwordEncoder.encode("ChangeMe123"))
                .isActive(true)
                .build();

        merchantUserRepository.save(owner);

        log.info("Merchant created: {}", merchant.getMerchantId());
        return mapToResponse(merchant);
    }

    @Override
    public MerchantResponse getMerchantById(UUID merchantId) {
        return mapToResponse(findMerchant(merchantId));
    }

    @Override
    public Page<MerchantResponse> listMerchants(MerchantStatus status, String q, int page, int size) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), 100),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return merchantRepository.search(status, q, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public MerchantResponse deactivateMerchant(UUID merchantId) {
        Merchant merchant = findMerchant(merchantId);
        merchant.setStatus(MerchantStatus.INACTIVE);
        return mapToResponse(merchant);
    }

    @Override
    public MerchantResponse activateMerchant(UUID merchantId) {
        Merchant merchant = findMerchant(merchantId);
        merchant.setStatus(MerchantStatus.ACTIVE);
        return mapToResponse(merchant);
    }

    private Merchant findMerchant(UUID merchantId) {
        return merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found: " + merchantId));
    }

    private MerchantResponse mapToResponse(Merchant m) {
        return new MerchantResponse(
                m.getMerchantId(),
                m.getBusinessName(),
                m.getBusinessCategory(),
                m.getPhoneNumber(),
                m.getEmail(),
                m.getAddressText(),
                m.getDistrict(),
                m.getSector(),
                m.getCell(),
                m.getVillage(),
                m.getStatus().name()
        );
    }
}
