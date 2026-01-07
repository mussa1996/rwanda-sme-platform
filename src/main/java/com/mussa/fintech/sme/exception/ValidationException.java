package com.mussa.fintech.sme.exception;

import com.mussa.fintech.sme.common.enums.ErrorCode;

public class ValidationException extends BusinessException {

    public ValidationException(String message) {
        super(message, 400, ErrorCode.VALIDATION_ERROR);
    }
}

