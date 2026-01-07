package com.mussa.fintech.sme.exception;


import com.mussa.fintech.sme.common.enums.ErrorCode;

public class WebhookException extends BusinessException {

    public WebhookException(String message) {
        super(message, 401, ErrorCode.INVALID_WEBHOOK_SIGNATURE);
    }
}

