package com.user12043.fxrate.dto.exception;

public record FxRateExceptionResponse(String message, String statusInfo, String details) {
    public FxRateExceptionResponse {
        statusInfo = "FAIL";
    }

    public FxRateExceptionResponse(String message, Exception exception) {
        this(message, "FAIL", exception.getMessage());
    }
}
