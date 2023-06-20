package com.user12043.fxrate.dto.exception;

/**
 * The entity object used when an exception occurred in the services.
 * Should include exception information provided by the service
 *
 * @param message    Should be the message provided by service
 * @param statusInfo Will be "FAIL" to be aligned with default response which has "OK"
 * @param details    Should be the details of source exception
 */
public record FxRateExceptionResponse(String message, String statusInfo, String details) {
    public FxRateExceptionResponse {
        statusInfo = "FAIL";
    }

    public FxRateExceptionResponse(String message, Exception exception) {
        this(message, "FAIL", exception.getMessage());
    }
}
