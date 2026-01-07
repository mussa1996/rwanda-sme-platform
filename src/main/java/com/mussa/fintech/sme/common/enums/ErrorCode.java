package com.mussa.fintech.sme.common.enums;

public enum ErrorCode {

    // General
    INTERNAL_ERROR,
    VALIDATION_ERROR,

    // Auth & Security
    UNAUTHORIZED,
    FORBIDDEN,

    // Domain
    RESOURCE_NOT_FOUND,
    CONFLICT,

    // Payments
    PAYMENT_FAILED,
    DUPLICATE_PAYMENT,
    INVALID_WEBHOOK_SIGNATURE,

    // Inventory
    INSUFFICIENT_STOCK
}

