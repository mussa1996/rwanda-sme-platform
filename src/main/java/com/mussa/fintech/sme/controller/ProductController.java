package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.DataResponse;
import com.mussa.fintech.sme.dto.products.CreateProductRequest;
import com.mussa.fintech.sme.dto.products.ProductResponse;
import com.mussa.fintech.sme.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/merchants/{merchantId}/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<DataResponse<ProductResponse>> createProduct(
            @PathVariable UUID merchantId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        ProductResponse resp = productService.createProduct(merchantId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponse.created("Product created successfully", resp));
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<ProductResponse>>> getProducts(@PathVariable UUID merchantId) {
        List<ProductResponse> resp = productService.getProducts(merchantId);
        return ResponseEntity.ok(DataResponse.ok("Products retrieved successfully", resp));
    }
}
