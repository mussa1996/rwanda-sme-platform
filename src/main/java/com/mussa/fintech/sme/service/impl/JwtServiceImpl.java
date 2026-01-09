package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.config.JwtUserDetails;
import com.mussa.fintech.sme.service.JwtService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private static final String BEARER = "Bearer ";

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public JwtServiceImpl(
            @Value("${security.jwt.publicKeyBase64}") String publicKeyBase64,
            @Value("${security.jwt.privateKeyBase64}") String privateKeyBase64
    ) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");

            this.publicKey = keyFactory.generatePublic(
                    new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64))
            );

            this.privateKey = keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64))
            );

        } catch (Exception e) {
            throw new IllegalStateException("Failed to load EC key pair", e);
        }
    }

    // ---------------------------------------------------------
    // Token validation
    // ---------------------------------------------------------
    @Override
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT expired: {}", e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT: {}", e.getMessage());
        }
        return false;
    }

    // ---------------------------------------------------------
    // Token parsing
    // ---------------------------------------------------------
    @Override
    public JwtUserDetails parseToken(String token) {
        Claims claims = parseClaims(token);

        UUID merchantUserId = UUID.fromString(claims.getSubject());
        UUID merchantId = UUID.fromString(claims.get("merchantId", String.class));
        String role = claims.get("role", String.class);

        return new JwtUserDetails(merchantId, merchantUserId, role);
    }

    // ---------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------
    private Claims parseClaims(String token) {
        String jwt = extractToken(token);

        return Jwts.parserBuilder()
                .setSigningKey(publicKey)     // ✅ VERIFY with PUBLIC key
                .setAllowedClockSkewSeconds(60)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private String extractToken(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("JWT token is missing");
        }
        return token.startsWith(BEARER)
                ? token.substring(BEARER.length()).trim()
                : token.trim();
    }

    // ---------------------------------------------------------
    // (Optional) Token generation – if this service issues tokens
    // ---------------------------------------------------------
    public String generateToken(UUID merchantUserId, UUID merchantId, String role) {
        return Jwts.builder()
                .setSubject(merchantUserId.toString())
                .claim("merchantId", merchantId.toString())
                .claim("role", role)
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 3600_000))
                .signWith(privateKey, SignatureAlgorithm.ES256) // ✅ SIGN with PRIVATE key
                .compact();
    }
}
