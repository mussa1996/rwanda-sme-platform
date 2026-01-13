package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.DataResponse;
import com.mussa.fintech.sme.common.dto.PagedResponse;
import com.mussa.fintech.sme.common.enums.MerchantStatus;
import com.mussa.fintech.sme.dto.merchants.CreateMerchantRequest;
import com.mussa.fintech.sme.dto.merchants.MerchantResponse;
import com.mussa.fintech.sme.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    /* ---------------- Create ---------------- */

    @PostMapping
    public ResponseEntity<DataResponse<MerchantResponse>> createMerchant(
            @Valid @RequestBody CreateMerchantRequest request
    ) {
        MerchantResponse resp = merchantService.createMerchant(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponse.created("Merchant created successfully", resp));
    }

    /* ---------------- Get by ID ---------------- */

    @GetMapping("/{merchantId}")
    public ResponseEntity<DataResponse<MerchantResponse>> getMerchant(
            @PathVariable UUID merchantId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Merchant retrieved successfully",
                        merchantService.getMerchantById(merchantId)
                )
        );
    }

    /* ---------------- Pagination + Filtering ---------------- */

    @GetMapping
    public ResponseEntity<PagedResponse<MerchantResponse>> listMerchants(
            @RequestParam(required = false) MerchantStatus status,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<MerchantResponse> result =
                merchantService.listMerchants(status, q, page, size);

        return ResponseEntity.ok(
                PagedResponse.of(
                        "Merchants retrieved successfully",
                        result.getContent(),
                        result.getNumber(),
                        result.getSize(),
                        result.getTotalElements(),
                        result.getTotalPages()
                )
        );
    }

    /* ---------------- Status Management ---------------- */

    @PatchMapping("/{merchantId}/deactivate")
    public ResponseEntity<DataResponse<MerchantResponse>> deactivate(
            @PathVariable UUID merchantId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Merchant deactivated successfully",
                        merchantService.deactivateMerchant(merchantId)
                )
        );
    }

    @PatchMapping("/{merchantId}/activate")
    public ResponseEntity<DataResponse<MerchantResponse>> activate(
            @PathVariable UUID merchantId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Merchant activated successfully",
                        merchantService.activateMerchant(merchantId)
                )
        );
    }
}
