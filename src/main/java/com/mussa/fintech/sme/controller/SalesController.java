package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.DataResponse;
import com.mussa.fintech.sme.dto.sales.CreateSaleRequest;
import com.mussa.fintech.sme.dto.sales.SaleResponse;
import com.mussa.fintech.sme.service.SalesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping
    public ResponseEntity<DataResponse<SaleResponse>> createSale(@Valid @RequestBody CreateSaleRequest request) {
        SaleResponse resp = salesService.createSale(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponse.created("Sale created successfully", resp));
    }
}
