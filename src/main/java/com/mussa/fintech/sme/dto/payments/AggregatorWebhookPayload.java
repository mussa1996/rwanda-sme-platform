package com.mussa.fintech.sme.dto.payments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AggregatorWebhookPayload {

    @NotBlank(message = "Payment provider is required")
    @Size(max = 50, message = "Provider name must not exceed 50 characters")
    private String provider;

    @NotBlank(message = "Transaction ID is required")
    @Size(max = 100, message = "Transaction ID must not exceed 100 characters")
    private String transactionId;

    @NotBlank(message = "Merchant reference is required")
    @Size(max = 100, message = "Merchant reference must not exceed 100 characters")
    private String merchantReference;

    @NotNull(message = "Payment amount is required")
    @DecimalMin(value = "0.01", message = "Payment amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Currency must be a valid ISO 4217 code (e.g. RWF)"
    )
    private String currency;

    @NotBlank(message = "Payment status is required")
    @Size(max = 50, message = "Status must not exceed 50 characters")
    private String status;

    @Size(max = 50, message = "Network must not exceed 50 characters")
    private String network;
}
