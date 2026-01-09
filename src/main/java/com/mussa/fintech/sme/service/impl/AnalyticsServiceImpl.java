package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.analytics.SalesSummaryResponse;
import com.mussa.fintech.sme.repository.SaleRepository;
import com.mussa.fintech.sme.service.AnalyticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final SaleRepository saleRepository;

    public AnalyticsServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public SalesSummaryResponse getTodaySales(UUID merchantId) {
        // placeholder logic – will be refined later
        return new SalesSummaryResponse(0, BigDecimal.ZERO);
    }

    @Override
    public SalesSummaryResponse getSalesBetween(
            UUID merchantId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return new SalesSummaryResponse(0, BigDecimal.ZERO);
    }
}

