package com.mussa.fintech.sme.exception;


import com.mussa.fintech.sme.common.enums.ErrorCode;

public class ConflictException extends BusinessException {

    public ConflictException(String message) {
        super(message, 409, ErrorCode.CONFLICT);
    }
}

