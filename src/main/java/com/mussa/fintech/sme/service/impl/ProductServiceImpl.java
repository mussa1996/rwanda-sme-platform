package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.products.CreateProductRequest;
import com.mussa.fintech.sme.dto.products.ProductResponse;
import com.mussa.fintech.sme.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public ProductResponse createProduct(
            UUID merchantId,
            CreateProductRequest request
    ) {
        return null;
    }

    @Override
    public List<ProductResponse> getProducts(UUID merchantId) {
        return List.of();
    }
}
