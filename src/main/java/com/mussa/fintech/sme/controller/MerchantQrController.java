package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.DataResponse;
import com.mussa.fintech.sme.dto.merchants.qr.CreateMerchantQrRequest;
import com.mussa.fintech.sme.dto.merchants.qr.MerchantQrResponse;
import com.mussa.fintech.sme.service.MerchantQrService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/merchants/{merchantId}/qrs")
@RequiredArgsConstructor
public class MerchantQrController {

    private final MerchantQrService merchantQrService;

    @PostMapping
    public ResponseEntity<DataResponse<MerchantQrResponse>> generateQr(
            @PathVariable UUID merchantId,
            @Valid @RequestBody CreateMerchantQrRequest request
    ) {
        MerchantQrResponse resp =
                merchantQrService.generateQr(merchantId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponse.created("QR created successfully", resp));
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<MerchantQrResponse>>> listQrs(
            @PathVariable UUID merchantId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Merchant QRs retrieved successfully",
                        merchantQrService.listMerchantQrs(merchantId)
                )
        );
    }

    @PatchMapping("/{merchantQrId}/activate")
    public ResponseEntity<DataResponse<MerchantQrResponse>> activateQr(
            @PathVariable UUID merchantId,
            @PathVariable UUID merchantQrId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "QR activated successfully",
                        merchantQrService.activateQr(merchantId, merchantQrId)
                )
        );
    }

    @PatchMapping("/{merchantQrId}/deactivate")
    public ResponseEntity<DataResponse<MerchantQrResponse>> deactivateQr(
            @PathVariable UUID merchantId,
            @PathVariable UUID merchantQrId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "QR deactivated successfully",
                        merchantQrService.deactivateQr(merchantId, merchantQrId)
                )
        );
    }
}
