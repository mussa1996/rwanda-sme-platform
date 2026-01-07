package com.mussa.fintech.sme.exception;


import com.mussa.fintech.sme.common.enums.ErrorCode;

public class PaymentException extends BusinessException {

    public PaymentException(String message) {
        super(message, 400, ErrorCode.PAYMENT_FAILED);
    }
}

