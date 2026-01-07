package com.mussa.fintech.sme.common.dto;

import java.time.OffsetDateTime;

public class ApiResponse {

    private boolean success;
    private String message;
    private int statusCode;
    private OffsetDateTime timestamp;

    protected ApiResponse(boolean success, String message, int statusCode) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = OffsetDateTime.now();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    /* ---------- Factory helpers ---------- */

    public static ApiResponse success(String message) {
        return new ApiResponse(true, message, 200);
    }

    public static ApiResponse created(String message) {
        return new ApiResponse(true, message, 201);
    }

    public static ApiResponse error(String message, int statusCode) {
        return new ApiResponse(false, message, statusCode);
    }
}

