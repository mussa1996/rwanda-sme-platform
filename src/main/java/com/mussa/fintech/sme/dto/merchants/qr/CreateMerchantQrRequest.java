package com.mussa.fintech.sme.dto.merchants.qr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mussa.fintech.sme.common.enums.QrType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateMerchantQrRequest {

    @NotNull(message = "qrType is required (STATIC or DYNAMIC)")
    private QrType qrType;

    /**
     * Optional: label/notes. Keep for future UI.
     */
    private String label;
}
