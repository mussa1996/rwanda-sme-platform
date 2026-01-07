package com.mussa.fintech.sme.dto.sales;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleResponse {

    private UUID saleId;
    private BigDecimal totalAmount;
    private String currency;
    private String status;
    private OffsetDateTime createdAt;

    public SaleResponse(UUID saleId,
                        BigDecimal totalAmount,
                        String currency,
                        String status,
                        OffsetDateTime createdAt) {

        this.saleId = saleId;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.status = status;
        this.createdAt = createdAt;
    }
}

