package com.mussa.fintech.sme.entity.notifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class NotificationResponse {

    private UUID notificationId;
    private String channel;
    private String recipient;
    private String message;
    private String status;
    private OffsetDateTime sentAt;
}
