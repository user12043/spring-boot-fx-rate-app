package com.user12043.fxrate.dto.exception;

import com.user12043.fxrate.util.FxRateExceptionUtil;
import com.user12043.fxrate.util.FxRateExceptionUtil.ErrorCode;

/**
 * The entity object used when an exception occurred in the services.
 * Should include exception information provided by the service
 *
 * @param message    Should be the message provided by service
 * @param statusInfo Will be "FAIL" to be aligned with default response which has "OK"
 * @param details    Should be the details of source exception
 */
public record FxRateExceptionResponse(ErrorCode errorCode, String message, String statusInfo, String details) {
    public FxRateExceptionResponse {
        statusInfo = "FAIL";
    }

    public FxRateExceptionResponse(ErrorCode errorCode, String message, Exception exception) {
        this(errorCode, message, "FAIL", exception.getMessage());
    }
}
