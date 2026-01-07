package com.mussa.fintech.sme.exception;

import com.mussa.fintech.sme.common.enums.ErrorCode;

public class DuplicatePaymentException extends BusinessException {

    public DuplicatePaymentException(String message) {
        super(message, 409, ErrorCode.DUPLICATE_PAYMENT);
    }
}

