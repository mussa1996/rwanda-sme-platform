package com.mussa.fintech.sme.service;

import java.util.UUID;

public interface QrCodeService {

    String generateMerchantQr(UUID merchantId);
}
