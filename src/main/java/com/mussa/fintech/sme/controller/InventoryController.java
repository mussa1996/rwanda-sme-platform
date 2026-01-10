package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.ApiResponse;
import com.mussa.fintech.sme.dto.inventory.RestockRequest;
import com.mussa.fintech.sme.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/restock")
    public ResponseEntity<ApiResponse> restock(@Valid @RequestBody RestockRequest request) {
        inventoryService.restock(request);
        return ResponseEntity.ok(ApiResponse.success("Restock completed successfully"));
    }
}
