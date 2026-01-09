package com.mussa.fintech.sme.service;

import com.mussa.fintech.sme.dto.auth.LoginRequest;
import com.mussa.fintech.sme.dto.auth.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
