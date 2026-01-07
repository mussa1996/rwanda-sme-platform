package com.mussa.fintech.sme.dto.analytics;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesSummaryResponse {
    private long totalTransactions;
    private BigDecimal totalRevenue;

    public SalesSummaryResponse(long totalTransactions,
                                BigDecimal totalRevenue) {
        this.totalTransactions = totalTransactions;
        this.totalRevenue = totalRevenue;
    }
}

