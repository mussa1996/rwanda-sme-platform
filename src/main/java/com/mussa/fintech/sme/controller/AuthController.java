package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.DataResponse;
import com.mussa.fintech.sme.dto.auth.LoginRequest;
import com.mussa.fintech.sme.dto.auth.LoginResponse;
import com.mussa.fintech.sme.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<DataResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse resp = authService.login(request);
        return ResponseEntity.ok(DataResponse.ok("Login successful", resp));
    }
}
