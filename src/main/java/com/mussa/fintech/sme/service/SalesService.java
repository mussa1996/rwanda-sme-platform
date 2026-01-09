package com.mussa.fintech.sme.service;


import com.mussa.fintech.sme.dto.sales.CreateSaleRequest;
import com.mussa.fintech.sme.dto.sales.SaleResponse;

public interface SalesService {

    SaleResponse createSale(CreateSaleRequest request);
}
