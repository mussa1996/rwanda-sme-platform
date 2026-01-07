package com.mussa.fintech.sme.dto.payments;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AggregatorWebhookPayload {

    private String provider;
    private String transactionId;
    private String merchantReference;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String network;
    private String rawPayload;
}

