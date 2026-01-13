package com.mussa.fintech.sme.service;

import com.mussa.fintech.sme.dto.merchants.qr.CreateMerchantQrRequest;
import com.mussa.fintech.sme.dto.merchants.qr.MerchantQrResponse;
import java.util.List;
import java.util.UUID;

public interface MerchantQrService {

    MerchantQrResponse generateQr(UUID merchantId, CreateMerchantQrRequest request);

    MerchantQrResponse activateQr(UUID merchantId, UUID merchantQrId);

    MerchantQrResponse deactivateQr(UUID merchantId, UUID merchantQrId);

    List<MerchantQrResponse> listMerchantQrs(UUID merchantId);
}
