package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.DataResponse;
import com.mussa.fintech.sme.dto.merchants.CreateMerchantRequest;
import com.mussa.fintech.sme.dto.merchants.MerchantResponse;
import com.mussa.fintech.sme.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity<DataResponse<MerchantResponse>> createMerchant(
            @Valid @RequestBody CreateMerchantRequest request
    ) {
        MerchantResponse resp = merchantService.createMerchant(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponse.created("Merchant created successfully", resp));
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<DataResponse<MerchantResponse>> getMerchant(@PathVariable UUID merchantId) {
        MerchantResponse resp = merchantService.getMerchant(merchantId);
        return ResponseEntity.ok(DataResponse.ok("Merchant retrieved successfully", resp));
    }
}
