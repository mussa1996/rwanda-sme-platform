package com.mussa.fintech.sme.service;



import com.mussa.fintech.sme.dto.merchants.CreateMerchantRequest;
import com.mussa.fintech.sme.dto.merchants.MerchantResponse;

import java.util.UUID;

public interface MerchantService {

    MerchantResponse createMerchant(CreateMerchantRequest request);

    MerchantResponse getMerchant(UUID merchantId);
}
