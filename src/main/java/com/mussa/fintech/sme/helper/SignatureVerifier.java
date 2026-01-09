package com.mussa.fintech.sme.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class SignatureVerifier {

    private final String webhookSecret;

    public SignatureVerifier(@Value("${payments.webhook.secret}") String webhookSecret) {
        this.webhookSecret = webhookSecret;
    }

    /**
     * Verifies an HMAC-SHA256 signature.
     * Common format: hex string of HMAC(rawBody, secret)
     */
    public boolean isValid(String rawBody, String signatureHeader) {
        if (signatureHeader == null || signatureHeader.isBlank()) return false;

        String computed = hmacSha256Hex(rawBody, webhookSecret);

        // Support either "sha256=<hex>" or "<hex>"
        String incoming = signatureHeader.trim();
        if (incoming.startsWith("sha256=")) {
            incoming = incoming.substring("sha256=".length());
        }

        return constantTimeEquals(computed, incoming);
    }

    private static String hmacSha256Hex(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] out = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return toHex(out);
        } catch (Exception e) {
            return "";
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private static boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) return false;
        if (a.length() != b.length()) return false;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}

