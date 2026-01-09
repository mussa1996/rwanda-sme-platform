package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.products.CreateProductRequest;
import com.mussa.fintech.sme.dto.products.ProductResponse;
import com.mussa.fintech.sme.entity.inventory.InventoryBalance;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import com.mussa.fintech.sme.entity.products.Product;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.InventoryBalanceRepository;
import com.mussa.fintech.sme.repository.MerchantRepository;
import com.mussa.fintech.sme.repository.ProductRepository;
import com.mussa.fintech.sme.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final MerchantRepository merchantRepo;
    private final InventoryBalanceRepository inventoryRepo;

    @Override
    public ProductResponse createProduct(UUID merchantId, CreateProductRequest req) {

        Merchant merchant = merchantRepo.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found"));

        Product product = productRepo.save(
                Product.builder()
                        .merchant(merchant)
                        .productName(req.getProductName())
                        .unitPrice(req.getUnitPrice())
                        .isActive(true)
                        .build()
        );

        inventoryRepo.save(
                InventoryBalance.builder()
                        .product(product)
                        .quantityOnHand(BigDecimal.ZERO)
                        .build()
        );

        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getUnitPrice(),
                true
        );
    }

    @Override
    public List<ProductResponse> getProducts(UUID merchantId) {
        return productRepo.findByMerchant_MerchantIdAndIsActiveTrue(merchantId)
                .stream()
                .map(p -> new ProductResponse(
                        p.getProductId(),
                        p.getProductName(),
                        p.getUnitPrice(),
                        p.isActive()))
                .toList();
    }
}

