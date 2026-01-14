package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.common.enums.*;
import com.mussa.fintech.sme.dto.sales.*;
import com.mussa.fintech.sme.entity.inventory.InventoryBalance;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import com.mussa.fintech.sme.entity.products.Product;
import com.mussa.fintech.sme.entity.sales.*;
import com.mussa.fintech.sme.exception.InventoryException;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.*;
import com.mussa.fintech.sme.service.InventoryService;
import com.mussa.fintech.sme.service.SalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SalesServiceImpl implements SalesService {

    private final SaleRepository saleRepository;
    private final MerchantRepository merchantRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;

    /* ---------------- Create Sale ---------------- */

    @Override
    public SaleResponse createSale(CreateSaleRequest req) {

        Merchant merchant = merchantRepository.findById(req.getMerchantId())
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found"));

        Sale sale = Sale.builder()
                .merchant(merchant)
                .saleChannel(req.getSaleChannel())
                .status(SaleStatus.PENDING)
                .currency("RWF")
                .subtotalAmount(BigDecimal.ZERO)
                .discountAmount(req.getDiscountAmount())
                .taxAmount(req.getTaxAmount())
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal subtotal = BigDecimal.ZERO;

        for (SaleItemRequest itemReq : req.getItems()) {

            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (!product.isActive()) {
                throw new InventoryException("Product is inactive");
            }

            BigDecimal lineTotal =
                    itemReq.getUnitPrice().multiply(itemReq.getQuantity());

            subtotal = subtotal.add(lineTotal);

            sale.getItems().add(
                    SaleItem.builder()
                            .sale(sale)
                            .product(product)
                            .quantity(itemReq.getQuantity())
                            .unitPrice(itemReq.getUnitPrice())
                            .lineTotal(lineTotal)
                            .build()
            );

            // 🔴 Reserve stock immediately
            inventoryService.deductStock(
                    merchant.getMerchantId(),
                    product.getProductId(),
                    itemReq.getQuantity(),
                    "SALE",
                    null
            );
        }

        BigDecimal total = subtotal
                .subtract(req.getDiscountAmount())
                .add(req.getTaxAmount());

        sale.setSubtotalAmount(subtotal);
        sale.setTotalAmount(total);

        saleRepository.save(sale);

        log.info("Sale created: {}", sale.getSaleId());

        return new SaleResponse(
                sale.getSaleId(),
                sale.getTotalAmount(),
                sale.getCurrency(),
                sale.getStatus().name(),
                sale.getCreatedAt()
        );
    }

    /* ---------------- Get Sale ---------------- */

    @Override
    public SaleDetailsResponse getSale(UUID merchantId, UUID saleId) {

        Sale sale = findSale(merchantId, saleId);

        return new SaleDetailsResponse(
                sale.getSaleId(),
                sale.getStatus().name(),
                sale.getSaleChannel().name(),
                sale.getSubtotalAmount(),
                sale.getDiscountAmount(),
                sale.getTaxAmount(),
                sale.getTotalAmount(),
                sale.getCurrency(),
                sale.getCreatedAt(),
                sale.getItems().stream()
                        .map(i -> new SaleItemResponse(
                                i.getProduct().getProductId(),
                                i.getProduct().getProductName(),
                                i.getQuantity(),
                                i.getUnitPrice(),
                                i.getLineTotal()
                        ))
                        .toList()
        );
    }

    /* ---------------- List Sales ---------------- */

    @Override
    public Page<SaleResponse> listSales(
            UUID merchantId,
            SaleStatus status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                page, size, Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Sale> result = (status == null)
                ? saleRepository.findAllByMerchant_MerchantId(merchantId, pageable)
                : saleRepository.findAllByMerchant_MerchantIdAndStatus(
                merchantId, status, pageable);

        return result.map(s ->
                new SaleResponse(
                        s.getSaleId(),
                        s.getTotalAmount(),
                        s.getCurrency(),
                        s.getStatus().name(),
                        s.getCreatedAt()
                )
        );
    }

    /* ---------------- Mark Paid ---------------- */

    @Override
    public SaleResponse markSalePaid(UUID merchantId, UUID saleId) {

        Sale sale = findSale(merchantId, saleId);

        if (sale.getStatus() != SaleStatus.PENDING) {
            throw new InventoryException("Only PENDING sales can be marked as PAID");
        }

        sale.setStatus(SaleStatus.PAID);

        return new SaleResponse(
                sale.getSaleId(),
                sale.getTotalAmount(),
                sale.getCurrency(),
                sale.getStatus().name(),
                sale.getCreatedAt()
        );
    }

    /* ---------------- Cancel Sale ---------------- */

    @Override
    public SaleResponse cancelSale(UUID merchantId, UUID saleId) {

        Sale sale = findSale(merchantId, saleId);

        if (sale.getStatus() != SaleStatus.PENDING) {
            throw new InventoryException("Only PENDING sales can be cancelled");
        }

        // 🔄 restore stock
        for (SaleItem item : sale.getItems()) {
            inventoryService.restock(
                    merchantId,
                    new com.mussa.fintech.sme.dto.inventory.RestockRequest() {{
                        setProductId(item.getProduct().getProductId());
                        setQuantity(item.getQuantity());
                    }}
            );
        }

        sale.setStatus(SaleStatus.CANCELLED);

        return new SaleResponse(
                sale.getSaleId(),
                sale.getTotalAmount(),
                sale.getCurrency(),
                sale.getStatus().name(),
                sale.getCreatedAt()
        );
    }

    /* ---------------- Helper ---------------- */

    private Sale findSale(UUID merchantId, UUID saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        if (!sale.getMerchant().getMerchantId().equals(merchantId)) {
            throw new ResourceNotFoundException("Sale does not belong to this merchant");
        }
        return sale;
    }
}
