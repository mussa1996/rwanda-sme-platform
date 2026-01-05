package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByMerchant_MerchantIdAndIsActiveTrue(UUID merchantId);
}
