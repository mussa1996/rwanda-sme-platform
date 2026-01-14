package com.mussa.fintech.sme.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class SaleDetailsResponse {

    private UUID saleId;
    private String status;
    private String saleChannel;

    private BigDecimal subtotalAmount;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String currency;

    private OffsetDateTime createdAt;

    private List<SaleItemResponse> items;
}
