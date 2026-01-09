package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.service.QrCodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

    @Override
    public String generateMerchantQr(UUID merchantId) {
        return "QR-" + merchantId;
    }
}
