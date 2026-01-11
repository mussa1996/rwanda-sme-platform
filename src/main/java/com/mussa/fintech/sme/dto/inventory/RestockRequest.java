package com.mussa.fintech.sme.dto.inventory;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestockRequest {

    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotNull(message = "Restock quantity is required")
    @DecimalMin(
            value = "0.0001",
            inclusive = true,
            message = "Restock quantity must be greater than zero"
    )
    private BigDecimal quantity;
    @Size(max = 255, message = "Note must not exceed 255 characters")
    private String note;
}

