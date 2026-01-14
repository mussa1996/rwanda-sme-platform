package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.*;
import com.mussa.fintech.sme.common.enums.NotificationStatus;
import com.mussa.fintech.sme.dto.notifications.*;
import com.mussa.fintech.sme.entity.notifications.NotificationResponse;
import com.mussa.fintech.sme.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/merchants/{merchantId}/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /* ---------------- Send SMS ---------------- */

    @PostMapping("/sms")
    public ResponseEntity<ApiResponse> sendSms(
            @PathVariable UUID merchantId,
            @Valid @RequestBody SmsRequest request
    ) {
        notificationService.sendSms(merchantId, request);
        return ResponseEntity.ok(
                ApiResponse.success("SMS queued/sent successfully")
        );
    }

    /* ---------------- List Notifications ---------------- */

    @GetMapping
    public ResponseEntity<PagedResponse<NotificationResponse>> list(
            @PathVariable UUID merchantId,
            @RequestParam(required = false) NotificationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<NotificationResponse> result =
                notificationService.listNotifications(
                        merchantId, status, page, size);

        return ResponseEntity.ok(
                PagedResponse.of(
                        "Notifications retrieved successfully",
                        result.getContent(),
                        result.getNumber(),
                        result.getSize(),
                        result.getTotalElements(),
                        result.getTotalPages()
                )
        );
    }
}
