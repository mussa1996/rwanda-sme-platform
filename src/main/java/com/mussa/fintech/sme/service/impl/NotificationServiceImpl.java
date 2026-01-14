package com.mussa.fintech.sme.service.impl;

import com.mussa.fintech.sme.common.enums.NotificationChannel;
import com.mussa.fintech.sme.common.enums.NotificationStatus;
import com.mussa.fintech.sme.dto.notifications.SmsRequest;
import com.mussa.fintech.sme.entity.merchants.Merchant;
import com.mussa.fintech.sme.entity.notifications.Notification;
import com.mussa.fintech.sme.entity.notifications.NotificationResponse;
import com.mussa.fintech.sme.exception.ResourceNotFoundException;
import com.mussa.fintech.sme.repository.MerchantRepository;
import com.mussa.fintech.sme.repository.NotificationRepository;
import com.mussa.fintech.sme.service.NotificationService;
import com.mussa.fintech.sme.service.SmsGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MerchantRepository merchantRepository;
    private final SmsGateway smsGateway;

    @Override
    public void sendSms(UUID merchantId, SmsRequest request) {

        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Merchant not found"));

        Notification notification = Notification.builder()
                .merchant(merchant)
                .channel(NotificationChannel.SMS)
                .recipient(request.getRecipient())
                .messageTemplate("RAW_SMS")
                .messageContent(request.getMessage())
                .status(NotificationStatus.PENDING)
                .build();

        notificationRepository.save(notification);

        try {
            String providerMessageId =
                    smsGateway.send(
                            request.getRecipient(),
                            request.getMessage()
                    );

            notification.setProviderMessageId(providerMessageId);
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(OffsetDateTime.now());

        } catch (Exception ex) {
            log.error("SMS sending failed", ex);
            notification.setStatus(NotificationStatus.FAILED);
        }
    }

    @Override
    public Page<NotificationResponse> listNotifications(
            UUID merchantId,
            NotificationStatus status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Notification> result =
                (status == null)
                        ? notificationRepository.findAllByMerchant_MerchantId(
                        merchantId, pageable)
                        : notificationRepository.findAllByMerchant_MerchantIdAndStatus(
                        merchantId, status, pageable);

        return result.map(n ->
                new NotificationResponse(
                        n.getNotificationId(),
                        n.getChannel().name(),
                        n.getRecipient(),
                        n.getMessageContent(),
                        n.getStatus().name(),
                        n.getSentAt()
                )
        );
    }
}
