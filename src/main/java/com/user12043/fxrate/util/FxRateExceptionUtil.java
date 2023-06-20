package com.user12043.fxrate.util;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class FxRateExceptionUtil extends HashMap<FxRateExceptionUtil.ErrorCode, FxRateExceptionUtil.ErrorInfo> {
    private static final FxRateExceptionUtil INSTANCE = new FxRateExceptionUtil();

    private FxRateExceptionUtil() {
        put(ErrorCode.UNEXPECTED, new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred"));
        put(ErrorCode.NO_DATA, new ErrorInfo(HttpStatus.NOT_FOUND, "Couldn't find the requested data"));
        put(ErrorCode.INVALID_INPUT, new ErrorInfo(HttpStatus.BAD_REQUEST, "Invalid input provided"));
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
        INVALID_INPUT
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
