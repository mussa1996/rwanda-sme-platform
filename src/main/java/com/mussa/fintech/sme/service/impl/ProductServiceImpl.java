package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.dto.products.CreateProductRequest;
import com.mussa.fintech.sme.dto.products.ProductResponse;
import com.mussa.fintech.sme.entity.inventory.InventoryBalance;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import com.mussa.fintech.sme.entity.products.Product;
import com.mussa.fintech.sme.exception.ConflictException;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.InventoryBalanceRepository;
import com.mussa.fintech.sme.repository.MerchantRepository;
import com.mussa.fintech.sme.repository.ProductRepository;
import com.mussa.fintech.sme.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;
    private final InventoryBalanceRepository inventoryBalanceRepository;

    /* ---------------- Create ---------------- */

    @Override
    public ProductResponse createProduct(UUID merchantId, CreateProductRequest req) {

        Merchant merchant = findMerchant(merchantId);

        if (productRepository.existsByMerchant_MerchantIdAndProductNameIgnoreCase(
                merchantId, req.getProductName())) {
            throw new ConflictException("Product with this name already exists for this merchant");
        }

        Product product = Product.builder()
                .merchant(merchant)
                .productName(req.getProductName())
                .sku(req.getSku())
                .unitPrice(req.getUnitPrice())
                .isActive(true)
                .build();

        productRepository.save(product);

        // Inventory initialized automatically
        inventoryBalanceRepository.save(
                InventoryBalance.builder()
                        .product(product)
                        .quantityOnHand(java.math.BigDecimal.ZERO)
                        .build()
        );

        log.info("Product created: {}", product.getProductId());
        return map(product);
    }

    /* ---------------- Update ---------------- */

    @Override
    public ProductResponse updateProduct(
            UUID merchantId,
            UUID productId,
            CreateProductRequest req
    ) {
        Product product = findProduct(merchantId, productId);

        product.setProductName(req.getProductName());
        product.setSku(req.getSku());
        product.setUnitPrice(req.getUnitPrice());

        return map(product);
    }

    /* ---------------- Get ---------------- */

    @Override
    public ProductResponse getProduct(UUID merchantId, UUID productId) {
        return map(findProduct(merchantId, productId));
    }

    /* ---------------- List (Pagination + Filter) ---------------- */

    @Override
    public Page<ProductResponse> listProducts(
            UUID merchantId,
            Boolean active,
            String q,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), 100),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return productRepository.search(merchantId, active, q, pageable)
                .map(this::map);
    }

    /* ---------------- Activate / Deactivate ---------------- */

    @Override
    public ProductResponse deactivateProduct(UUID merchantId, UUID productId) {
        Product product = findProduct(merchantId, productId);
        product.setActive(false);
        return map(product);
    }

    @Override
    public ProductResponse activateProduct(UUID merchantId, UUID productId) {
        Product product = findProduct(merchantId, productId);
        product.setActive(true);
        return map(product);
    }

    /* ---------------- Helpers ---------------- */

    private Merchant findMerchant(UUID merchantId) {
        return merchantRepository.findById(merchantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Merchant not found: " + merchantId));
    }

    private Product findProduct(UUID merchantId, UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found: " + productId));

        if (!product.getMerchant().getMerchantId().equals(merchantId)) {
            throw new ResourceNotFoundException("Product does not belong to this merchant");
        }
        return product;
    }

    private ProductResponse map(Product p) {
        return new ProductResponse(
                p.getProductId(),
                p.getProductName(),
                p.getSku(),
                p.getUnitPrice(),
                p.isActive()
        );
    }
}
