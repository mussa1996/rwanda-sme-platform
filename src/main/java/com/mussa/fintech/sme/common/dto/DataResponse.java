package com.mussa.fintech.sme.common.dto;


public class DataResponse<T> extends ApiResponse {

    private T data;

    private DataResponse(boolean success, String message, int statusCode, T data) {
        super(success, message, statusCode);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    /* ---------- Factory helpers ---------- */

    public static <T> DataResponse<T> ok(String message, T data) {
        return new DataResponse<>(true, message, 200, data);
    }

    public static <T> DataResponse<T> created(String message, T data) {
        return new DataResponse<>(true, message, 201, data);
    }
}

