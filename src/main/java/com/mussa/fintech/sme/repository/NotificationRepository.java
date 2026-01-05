package com.mussa.fintech.sme.repository;


import com.mussa.fintech.sme.common.enums.NotificationStatus;
import com.mussa.fintech.sme.entity.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository
        extends JpaRepository<Notification, UUID> {

    List<Notification> findByStatus(NotificationStatus status);
}
