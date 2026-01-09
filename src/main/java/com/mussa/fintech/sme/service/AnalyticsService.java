package com.mussa.fintech.sme.service;


import com.mussa.fintech.sme.dto.analytics.SalesSummaryResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface AnalyticsService {

    SalesSummaryResponse getTodaySales(UUID merchantId);

    SalesSummaryResponse getSalesBetween(
            UUID merchantId,
            LocalDate startDate,
            LocalDate endDate
    );
}
