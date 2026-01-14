package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.*;
import com.mussa.fintech.sme.dto.inventory.*;
import com.mussa.fintech.sme.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/merchants/{merchantId}/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /* ---------------- Get Balance ---------------- */

    @GetMapping("/products/{productId}")
    public ResponseEntity<DataResponse<InventoryBalanceResponse>> getBalance(
            @PathVariable UUID merchantId,
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Inventory balance retrieved successfully",
                        inventoryService.getBalance(merchantId, productId)
                )
        );
    }

    /* ---------------- Restock ---------------- */

    @PostMapping("/restock")
    public ResponseEntity<ApiResponse> restock(
            @PathVariable UUID merchantId,
            @Valid @RequestBody RestockRequest request
    ) {
        inventoryService.restock(merchantId, request);
        return ResponseEntity.ok(
                ApiResponse.success("Restock completed successfully")
        );
    }

    /* ---------------- Movement History ---------------- */

    @GetMapping("/movements")
    public ResponseEntity<PagedResponse<InventoryMovementResponse>> movements(
            @PathVariable UUID merchantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<InventoryMovementResponse> result =
                inventoryService.listMovements(merchantId, page, size);

        return ResponseEntity.ok(
                PagedResponse.of(
                        "Inventory movements retrieved successfully",
                        result.getContent(),
                        result.getNumber(),
                        result.getSize(),
                        result.getTotalElements(),
                        result.getTotalPages()
                )
        );
    }
}
