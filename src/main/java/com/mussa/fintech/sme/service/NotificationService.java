package com.mussa.fintech.sme.service;

import com.mussa.fintech.sme.common.enums.NotificationStatus;
import com.mussa.fintech.sme.dto.notifications.SmsRequest;
import com.mussa.fintech.sme.entity.notifications.NotificationResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface NotificationService {
//    void notifyPaymentSuccess(String phone, String message);

    void sendSms(UUID merchantId, SmsRequest request);

    Page<NotificationResponse> listNotifications(
            UUID merchantId,
            NotificationStatus status,
            int page,
            int size
    );
}

