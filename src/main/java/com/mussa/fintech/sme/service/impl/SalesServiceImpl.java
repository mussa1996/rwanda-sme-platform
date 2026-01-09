package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.sales.CreateSaleRequest;
import com.mussa.fintech.sme.dto.sales.SaleResponse;
import com.mussa.fintech.sme.service.SalesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalesServiceImpl implements SalesService {

    @Override
    public SaleResponse createSale(CreateSaleRequest request) {
        // create pending sale
        return null;
    }
}
