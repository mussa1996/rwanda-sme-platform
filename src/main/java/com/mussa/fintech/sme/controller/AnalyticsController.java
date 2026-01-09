package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.DataResponse;
import com.mussa.fintech.sme.dto.analytics.SalesSummaryResponse;
import com.mussa.fintech.sme.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/merchants/{merchantId}/sales/today")
    public ResponseEntity<DataResponse<SalesSummaryResponse>> todaySales(@PathVariable UUID merchantId) {
        SalesSummaryResponse resp = analyticsService.getTodaySales(merchantId);
        return ResponseEntity.ok(DataResponse.ok("Today's sales summary retrieved", resp));
    }

    @GetMapping("/merchants/{merchantId}/sales")
    public ResponseEntity<DataResponse<SalesSummaryResponse>> salesBetween(
            @PathVariable UUID merchantId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        SalesSummaryResponse resp = analyticsService.getSalesBetween(merchantId, startDate, endDate);
        return ResponseEntity.ok(DataResponse.ok("Sales summary retrieved", resp));
    }
}
