package com.mussa.fintech.sme.exception;


import com.mussa.fintech.sme.common.enums.ErrorCode;

public abstract class BusinessException extends RuntimeException {

    private final int statusCode;
    private final ErrorCode errorCode;

    protected BusinessException(
            String message,
            int statusCode,
            ErrorCode errorCode) {

        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

