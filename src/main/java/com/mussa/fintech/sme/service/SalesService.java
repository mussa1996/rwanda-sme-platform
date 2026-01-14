package com.mussa.fintech.sme.service;


import com.mussa.fintech.sme.common.enums.SaleStatus;
import com.mussa.fintech.sme.dto.sales.CreateSaleRequest;
import com.mussa.fintech.sme.dto.sales.SaleDetailsResponse;
import com.mussa.fintech.sme.dto.sales.SaleResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface SalesService {

    SaleResponse createSale(CreateSaleRequest request);
    SaleDetailsResponse getSale(UUID merchantId, UUID saleId);

    Page<SaleResponse> listSales(
            UUID merchantId,
            SaleStatus status,
            int page,
            int size
    );

    SaleResponse markSalePaid(UUID merchantId, UUID saleId);

    SaleResponse cancelSale(UUID merchantId, UUID saleId);
}
