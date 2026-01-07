package com.mussa.fintech.sme.exception;


import com.mussa.fintech.sme.common.enums.ErrorCode;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(String message) {
        super(message, 401, ErrorCode.UNAUTHORIZED);
    }
}

