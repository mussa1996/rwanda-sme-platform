package com.mussa.fintech.sme.dto.payments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {

    private UUID paymentId;
    private UUID saleId;
    private String provider;
    private String providerTxnId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private OffsetDateTime paidAt;
}
