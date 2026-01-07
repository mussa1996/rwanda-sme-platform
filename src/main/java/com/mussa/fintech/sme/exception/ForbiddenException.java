package com.mussa.fintech.sme.exception;

import com.mussa.fintech.sme.common.enums.ErrorCode;

public class ForbiddenException extends BusinessException {

    public ForbiddenException(String message) {
        super(message, 403, ErrorCode.FORBIDDEN);
    }
}

