package com.mussa.fintech.sme.service;

import com.mussa.fintech.sme.config.JwtUserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface JwtService {
    boolean isTokenValid(String token);
    JwtUserDetails parseToken(String token);
    String generateToken(UUID merchantUserId, UUID merchantId, String role);

}
