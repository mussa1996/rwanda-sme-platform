package com.mussa.fintech.sme.dto.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
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
