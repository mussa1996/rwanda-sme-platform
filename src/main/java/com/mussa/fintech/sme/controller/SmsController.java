package com.mussa.fintech.sme.controller;

import com.mussa.fintech.sme.common.dto.ApiResponse;
import com.mussa.fintech.sme.dto.notifications.SmsRequest;
import com.mussa.fintech.sme.service.SmsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/sms")
    public ResponseEntity<ApiResponse> sendSms(@Valid @RequestBody SmsRequest request) {
        smsService.sendSms(request.getRecipient(), request.getMessage());
        return ResponseEntity.ok(ApiResponse.success("SMS queued/sent successfully"));
    }
}
