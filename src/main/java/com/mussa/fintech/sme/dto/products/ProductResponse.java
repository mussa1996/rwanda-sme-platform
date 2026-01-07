package com.mussa.fintech.sme.dto.products;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

    private UUID productId;
    private String productName;
    private BigDecimal unitPrice;
    private boolean isActive;

    public ProductResponse(UUID productId, String productName,
                           BigDecimal unitPrice, boolean isActive) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.isActive = isActive;
    }
}

