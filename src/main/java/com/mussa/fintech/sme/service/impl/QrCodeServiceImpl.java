package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.service.QrCodeService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Override
    public String generateMerchantQr(UUID merchantId) {
        return "QR-" + merchantId;
    }
}
