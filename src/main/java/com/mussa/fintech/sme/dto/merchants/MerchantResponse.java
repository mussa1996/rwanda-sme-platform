package com.mussa.fintech.sme.dto.merchants;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantResponse {

    private UUID merchantId;
    private String businessName;
    private String businessCategory;
    private String phoneNumber;
    private String status;

    public MerchantResponse(
            UUID merchantId,
            String businessName,
            String businessCategory,
            String phoneNumber,
            String status) {

        this.merchantId = merchantId;
        this.businessName = businessName;
        this.businessCategory = businessCategory;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

}

