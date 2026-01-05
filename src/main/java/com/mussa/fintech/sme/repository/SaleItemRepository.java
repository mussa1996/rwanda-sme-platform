package com.mussa.fintech.sme.repository;

import com.mussa.fintech.sme.entity.sales.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SaleItemRepository extends JpaRepository<SaleItem, UUID> {

    List<SaleItem> findBySale_SaleId(UUID saleId);
}
