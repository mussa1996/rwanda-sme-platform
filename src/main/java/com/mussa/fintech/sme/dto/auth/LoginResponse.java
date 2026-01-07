package com.mussa.fintech.sme.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    private String token;
    private UUID merchantId;
    private UUID merchantUserId;
    private String role;

    public LoginResponse(String token, UUID merchantId, UUID merchantUserId, String role) {
        this.token = token;
        this.merchantId = merchantId;
        this.merchantUserId = merchantUserId;
        this.role = role;
    }
}

