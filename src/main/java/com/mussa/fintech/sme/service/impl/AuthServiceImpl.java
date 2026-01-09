package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.auth.LoginRequest;
import com.mussa.fintech.sme.dto.auth.LoginResponse;
import com.mussa.fintech.sme.service.AuthService;
import com.mussa.fintech.sme.service.JwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;

    public AuthServiceImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // TODO: validate user credentials from DB
        // Temporary stub
        throw new UnsupportedOperationException("Login logic not implemented yet");
    }
}
