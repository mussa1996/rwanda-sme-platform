package com.mussa.fintech.sme.dto.merchants;


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
public class MerchantResponse {
    private UUID merchantId;

    private String businessName;
    private String businessCategory;

    private String phoneNumber;
    private String email;
    private String addressText;

    private String district;
    private String sector;
    private String cell;
    private String village;

    private String status;



}

