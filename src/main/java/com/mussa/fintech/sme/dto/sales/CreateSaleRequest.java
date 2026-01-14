package com.mussa.fintech.sme.dto.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mussa.fintech.sme.common.enums.SaleChannel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSaleRequest {

    @NotNull(message = "Merchant ID is required")
    private UUID merchantId;

    @NotNull(message = "Sale channel is required")
    private SaleChannel saleChannel;

    @NotEmpty(message = "At least one sale item is required")
    @Valid
    private List<SaleItemRequest> items;

    @DecimalMin(value = "0.00")
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @DecimalMin(value = "0.00")
    private BigDecimal taxAmount = BigDecimal.ZERO;
}
