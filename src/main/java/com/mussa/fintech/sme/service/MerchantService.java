package com.mussa.fintech.sme.service;



import com.mussa.fintech.sme.common.enums.MerchantStatus;
import com.mussa.fintech.sme.dto.merchants.CreateMerchantRequest;
import com.mussa.fintech.sme.dto.merchants.MerchantResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface MerchantService {

    MerchantResponse createMerchant(CreateMerchantRequest request);

    MerchantResponse getMerchantById(UUID merchantId);
    Page<MerchantResponse> listMerchants(MerchantStatus status, String q, int page, int size);

    MerchantResponse deactivateMerchant(UUID merchantId);

    MerchantResponse activateMerchant(UUID merchantId);
}
