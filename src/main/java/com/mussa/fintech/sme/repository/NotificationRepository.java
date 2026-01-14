package com.mussa.fintech.sme.repository;


import com.mussa.fintech.sme.common.enums.NotificationStatus;
import com.mussa.fintech.sme.entity.notifications.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository
        extends JpaRepository<Notification, UUID> {

    List<Notification> findByStatus(NotificationStatus status);
    Page<Notification> findAllByMerchant_MerchantId(
            UUID merchantId,
            Pageable pageable
    );

    Page<Notification> findAllByMerchant_MerchantIdAndStatus(
            UUID merchantId,
            NotificationStatus status,
            Pageable pageable
    );
}
