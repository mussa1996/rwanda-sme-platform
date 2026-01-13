package com.mussa.fintech.sme.dto.merchants.qr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantQrResponse {

    private UUID merchantQrId;
    private UUID merchantId;

    private String qrType;
    private String qrPayload;

    private boolean active;
}
