package com.mussa.fintech.sme.exception;

import com.mussa.fintech.sme.common.enums.ErrorCode;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message) {
        super(message, 404, ErrorCode.RESOURCE_NOT_FOUND);
    }
}

