package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.analytics.SalesSummaryResponse;
import com.mussa.fintech.sme.repository.SaleRepository;
import com.mussa.fintech.sme.service.AnalyticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final SaleRepository saleRepository;

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

