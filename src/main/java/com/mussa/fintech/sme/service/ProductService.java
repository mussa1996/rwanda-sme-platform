package com.mussa.fintech.sme.service;

import com.mussa.fintech.sme.dto.products.CreateProductRequest;
import com.mussa.fintech.sme.dto.products.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(
            UUID merchantId,
            CreateProductRequest request
    );

    ProductResponse updateProduct(
            UUID merchantId,
            UUID productId,
            CreateProductRequest request
    );

    ProductResponse getProduct(UUID merchantId, UUID productId);

    Page<ProductResponse> listProducts(
            UUID merchantId,
            Boolean active,
            String q,
            int page,
            int size
    );

    ProductResponse deactivateProduct(UUID merchantId, UUID productId);

    ProductResponse activateProduct(UUID merchantId, UUID productId);
}
