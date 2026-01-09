package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.auth.LoginRequest;
import com.mussa.fintech.sme.dto.auth.LoginResponse;
import com.mussa.fintech.sme.entity.merchants.MerchantUser;
import com.mussa.fintech.sme.exception.UnauthorizedException;
import com.mussa.fintech.sme.repository.MerchantUserRepository;
import com.mussa.fintech.sme.service.AuthService;
import com.mussa.fintech.sme.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final MerchantUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        String normalizedPhone = normalizePhone(request.getPhoneNumber());

        MerchantUser user = userRepository.findByPhoneNumber(normalizedPhone)
                .orElseThrow(() -> unauthorized());

        if (!user.isActive()) {
            throw new UnauthorizedException("User account is disabled");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw unauthorized();
        }

        String token = jwtService.generateToken(
                user.getMerchant().getMerchantId(),
                user.getMerchantUserId(),
                user.getRole().name()
        );

        return LoginResponse.builder()
                .accessToken(token)
                .merchantId(user.getMerchant().getMerchantId())
                .merchantUserId(user.getMerchantUserId())
                .role(user.getRole().name())
                .build();
    }

    private UnauthorizedException unauthorized() {
        return new UnauthorizedException("Invalid phone number or password");
    }

    private String normalizePhone(String phone) {
        return phone.trim().replaceAll("\\s+", "");
    }
}
