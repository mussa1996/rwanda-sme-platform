package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.*;
import com.mussa.fintech.sme.common.enums.SaleStatus;
import com.mussa.fintech.sme.dto.sales.*;
import com.mussa.fintech.sme.service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/merchants/{merchantId}/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    /* ---------------- Create ---------------- */

    @PostMapping
    public ResponseEntity<DataResponse<SaleResponse>> createSale(
            @PathVariable UUID merchantId,
            @Valid @RequestBody CreateSaleRequest request
    ) {
        request.setMerchantId(merchantId);

        SaleResponse resp = salesService.createSale(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponse.created("Sale created successfully", resp));
    }

    /* ---------------- Get ---------------- */

    @GetMapping("/{saleId}")
    public ResponseEntity<DataResponse<SaleDetailsResponse>> getSale(
            @PathVariable UUID merchantId,
            @PathVariable UUID saleId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Sale retrieved successfully",
                        salesService.getSale(merchantId, saleId)
                )
        );
    }

    /* ---------------- List ---------------- */

    @GetMapping
    public ResponseEntity<PagedResponse<SaleResponse>> listSales(
            @PathVariable UUID merchantId,
            @RequestParam(required = false) SaleStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<SaleResponse> result =
                salesService.listSales(merchantId, status, page, size);

        return ResponseEntity.ok(
                PagedResponse.of(
                        "Sales retrieved successfully",
                        result.getContent(),
                        result.getNumber(),
                        result.getSize(),
                        result.getTotalElements(),
                        result.getTotalPages()
                )
        );
    }

    /* ---------------- Status Actions ---------------- */

    @PatchMapping("/{saleId}/paid")
    public ResponseEntity<DataResponse<SaleResponse>> markPaid(
            @PathVariable UUID merchantId,
            @PathVariable UUID saleId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Sale marked as PAID",
                        salesService.markSalePaid(merchantId, saleId)
                )
        );
    }

    @PatchMapping("/{saleId}/cancel")
    public ResponseEntity<DataResponse<SaleResponse>> cancel(
            @PathVariable UUID merchantId,
            @PathVariable UUID saleId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Sale cancelled successfully",
                        salesService.cancelSale(merchantId, saleId)
                )
        );
    }
}
