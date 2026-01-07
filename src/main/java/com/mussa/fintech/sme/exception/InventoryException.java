package com.mussa.fintech.sme.exception;

import com.mussa.fintech.sme.common.enums.ErrorCode;

public class InventoryException extends BusinessException {

    public InventoryException(String message) {
        super(message, 400, ErrorCode.INSUFFICIENT_STOCK);
    }
}

