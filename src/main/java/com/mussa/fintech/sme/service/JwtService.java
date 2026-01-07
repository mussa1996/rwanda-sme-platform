package com.mussa.fintech.sme.service;

import com.mussa.fintech.sme.config.JwtUserDetails;

public interface JwtService {
    boolean isTokenValid(String token);
    JwtUserDetails parseToken(String token);

}
