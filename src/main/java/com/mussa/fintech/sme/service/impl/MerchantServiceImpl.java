package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.merchants.CreateMerchantRequest;
import com.mussa.fintech.sme.dto.merchants.MerchantResponse;
import com.mussa.fintech.sme.service.MerchantService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Override
    public MerchantResponse createMerchant(CreateMerchantRequest request) {
        // persist merchant
        return null;
    }

    @Override
    public MerchantResponse getMerchant(UUID merchantId) {
        return null;
    }
}
