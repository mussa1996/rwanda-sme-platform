package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.DataResponse;
import com.mussa.fintech.sme.common.dto.PagedResponse;
import com.mussa.fintech.sme.dto.products.CreateProductRequest;
import com.mussa.fintech.sme.dto.products.ProductResponse;
import com.mussa.fintech.sme.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/merchants/{merchantId}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /* ---------------- Create ---------------- */

    @PostMapping
    public ResponseEntity<DataResponse<ProductResponse>> createProduct(
            @PathVariable UUID merchantId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        ProductResponse resp = productService.createProduct(merchantId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponse.created("Product created successfully", resp));
    }

    /* ---------------- Get one ---------------- */

    @GetMapping("/{productId}")
    public ResponseEntity<DataResponse<ProductResponse>> getProduct(
            @PathVariable UUID merchantId,
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Product retrieved successfully",
                        productService.getProduct(merchantId, productId)
                )
        );
    }

    /* ---------------- List (Pagination + Filter) ---------------- */

    @GetMapping
    public ResponseEntity<PagedResponse<ProductResponse>> listProducts(
            @PathVariable UUID merchantId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<ProductResponse> result =
                productService.listProducts(merchantId, active, q, page, size);

        return ResponseEntity.ok(
                PagedResponse.of(
                        "Products retrieved successfully",
                        result.getContent(),
                        result.getNumber(),
                        result.getSize(),
                        result.getTotalElements(),
                        result.getTotalPages()
                )
        );
    }

    /* ---------------- Update ---------------- */

    @PutMapping("/{productId}")
    public ResponseEntity<DataResponse<ProductResponse>> updateProduct(
            @PathVariable UUID merchantId,
            @PathVariable UUID productId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Product updated successfully",
                        productService.updateProduct(merchantId, productId, request)
                )
        );
    }

    /* ---------------- Activate / Deactivate ---------------- */

    @PatchMapping("/{productId}/deactivate")
    public ResponseEntity<DataResponse<ProductResponse>> deactivate(
            @PathVariable UUID merchantId,
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Product deactivated successfully",
                        productService.deactivateProduct(merchantId, productId)
                )
        );
    }

    @PatchMapping("/{productId}/activate")
    public ResponseEntity<DataResponse<ProductResponse>> activate(
            @PathVariable UUID merchantId,
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                DataResponse.ok(
                        "Product activated successfully",
                        productService.activateProduct(merchantId, productId)
                )
        );
    }
}
