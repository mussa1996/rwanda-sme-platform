package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.common.enums.SaleStatus;
import com.mussa.fintech.sme.dto.sales.CreateSaleRequest;
import com.mussa.fintech.sme.dto.sales.SaleItemRequest;
import com.mussa.fintech.sme.dto.sales.SaleResponse;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import com.mussa.fintech.sme.entity.products.Product;
import com.mussa.fintech.sme.entity.sales.Sale;
import com.mussa.fintech.sme.entity.sales.SaleItem;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.MerchantRepository;
import com.mussa.fintech.sme.repository.ProductRepository;
import com.mussa.fintech.sme.repository.SaleItemRepository;
import com.mussa.fintech.sme.repository.SaleRepository;
import com.mussa.fintech.sme.service.SalesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class SalesServiceImpl implements SalesService {

    private final SaleRepository saleRepo;
    private final SaleItemRepository itemRepo;
    private final ProductRepository productRepo;
    private final MerchantRepository merchantRepo;

    @Override
    public SaleResponse createSale(CreateSaleRequest req) {
        Merchant merchant = merchantRepo.findById(req.getMerchantId())
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found"));

        Sale sale = saleRepo.save(
                Sale.builder()
                        .merchant(merchant)
                        .status(SaleStatus.PENDING)
                        .currency("RWF")
                        .subtotalAmount(BigDecimal.ZERO)
                        .totalAmount(BigDecimal.ZERO)
                        .build()
        );


        BigDecimal total = BigDecimal.ZERO;

        for (SaleItemRequest item : req.getItems()) {
            Product product = productRepo.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            BigDecimal lineTotal =
                    item.getUnitPrice().multiply(item.getQuantity());

            total = total.add(lineTotal);

            itemRepo.save(
                    SaleItem.builder()
                            .sale(sale)
                            .product(product)
                            .quantity(item.getQuantity())
                            .unitPrice(item.getUnitPrice())
                            .lineTotal(lineTotal)
                            .build()
            );
        }

        sale.setTotalAmount(total);
        return new SaleResponse(
                sale.getSaleId(),
                total,
                sale.getCurrency(),
                sale.getStatus().name(),
                sale.getCreatedAt()
        );
    }
}

