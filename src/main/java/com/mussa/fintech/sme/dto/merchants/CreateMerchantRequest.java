package com.mussa.fintech.sme.dto.merchants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateMerchantRequest {

    @NotBlank
    private String businessName;

    private String businessCategory;

    @NotBlank
    private String phoneNumber;

    private String email;
    private String addressText;
    private String district;
    private String sector;
    private String cell;
    private String village;

}

