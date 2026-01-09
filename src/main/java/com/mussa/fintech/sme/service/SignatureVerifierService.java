package com.mussa.fintech.sme.service;

public interface SignatureVerifierService {
    boolean verify(String payload, String signature);
}

