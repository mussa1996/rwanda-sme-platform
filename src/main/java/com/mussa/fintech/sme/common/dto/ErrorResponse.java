package com.mussa.fintech.sme.common.dto;


import java.util.List;

public class ErrorResponse extends ApiResponse {

    private List<String> errors;

    public ErrorResponse(String message, int statusCode, List<String> errors) {
        super(false, message, statusCode);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}

