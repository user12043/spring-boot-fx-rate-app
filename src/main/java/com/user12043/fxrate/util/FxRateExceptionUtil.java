package com.user12043.fxrate.util;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * Global error information class for global error handling
 * Includes error codes and messages for defined cases
 */
public class FxRateExceptionUtil extends HashMap<FxRateExceptionUtil.ErrorCode, FxRateExceptionUtil.ErrorInfo> {
    private static final FxRateExceptionUtil INSTANCE = new FxRateExceptionUtil();

    private FxRateExceptionUtil() {
        put(ErrorCode.UNEXPECTED, new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred"));
        put(ErrorCode.NO_DATA, new ErrorInfo(HttpStatus.NOT_FOUND, "Couldn't find the requested data"));
        put(ErrorCode.INVALID_INPUT, new ErrorInfo(HttpStatus.BAD_REQUEST, "Invalid input provided"));
        put(ErrorCode.EXCHANGE_API_CONNECTION_FAIL, new ErrorInfo(HttpStatus.SERVICE_UNAVAILABLE, "Cannot connect to external exchange API"));
        put(ErrorCode.EXCHANGE_API_FORBIDDEN, new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR,
                "The external exchange API rejected the request. Please make sure that EXCHANGE_API_KEY variable is correct"));
    }

    public static HttpStatus getHttpStatus(ErrorCode code) {
        return INSTANCE.get(code).httpStatus;
    }

    public static String getMessage(ErrorCode code) {
        return INSTANCE.get(code).message;
    }

    public enum ErrorCode {
        UNEXPECTED,
        NO_DATA,
        INVALID_INPUT,
        EXCHANGE_API_CONNECTION_FAIL,
        EXCHANGE_API_FORBIDDEN
    }

    protected static class ErrorInfo {
        public HttpStatus httpStatus;
        public String message;

        public ErrorInfo(HttpStatus httpStatus, String message) {
            this.httpStatus = httpStatus;
            this.message = message;
        }
    }
}
