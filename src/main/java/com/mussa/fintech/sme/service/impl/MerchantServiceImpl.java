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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepo;
    private final MerchantUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MerchantResponse createMerchant(CreateMerchantRequest req) {

        if (merchantRepo.existsByPhoneNumber(req.getPhoneNumber())) {
            throw new ConflictException("Merchant already exists");
        }

        Merchant merchant = merchantRepo.save(
                Merchant.builder()
                        .businessName(req.getBusinessName())
                        .phoneNumber(req.getPhoneNumber())
                        .status(MerchantStatus.ACTIVE)
                        .build()
        );

        MerchantUser owner = MerchantUser.builder()
                .merchant(merchant)
                .phoneNumber(req.getPhoneNumber())
                .role(UserRole.OWNER)
                .passwordHash(passwordEncoder.encode("ChangeMe123"))
                .build();

        userRepo.save(owner);

        return new MerchantResponse(
                merchant.getMerchantId(),
                merchant.getBusinessName(),
                merchant.getBusinessCategory(),
                merchant.getPhoneNumber(),
                merchant.getStatus().name()
        );
    }

    @Override
    public MerchantResponse getMerchant(UUID merchantId) {
        Merchant merchant = merchantRepo.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found"));

        return new MerchantResponse(
                merchant.getMerchantId(),
                merchant.getBusinessName(),
                merchant.getBusinessCategory(),
                merchant.getPhoneNumber(),
                merchant.getStatus().name()
        );
    }
}

