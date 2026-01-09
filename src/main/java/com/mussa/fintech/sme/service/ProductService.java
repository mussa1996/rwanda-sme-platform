package com.mussa.fintech.sme.service;

import com.mussa.fintech.sme.dto.products.CreateProductRequest;
import com.mussa.fintech.sme.dto.products.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(
            UUID merchantId,
            CreateProductRequest request
    );

    List<ProductResponse> getProducts(UUID merchantId);
}
