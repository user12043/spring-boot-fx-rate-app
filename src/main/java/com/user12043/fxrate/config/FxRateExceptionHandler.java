package com.user12043.fxrate.config;

import com.user12043.fxrate.dto.exception.FxRateExceptionResponse;
import com.user12043.fxrate.util.FxRateExceptionUtil;
import com.user12043.fxrate.util.FxRateExceptionUtil.ErrorCode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class FxRateExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<?> handle(RuntimeException exception, WebRequest request, ErrorCode errorCode) {
        return handleExceptionInternal(
                exception,
                new FxRateExceptionResponse(FxRateExceptionUtil.getMessage(errorCode), exception),
                new HttpHeaders(),
                FxRateExceptionUtil.getHttpStatus(errorCode),
                request);
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class, NoSuchElementException.class})
    public ResponseEntity<?> handleNoData(RuntimeException exception, WebRequest request) {
        return handle(exception, request, ErrorCode.NO_DATA);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleIllegalArgument(RuntimeException exception, WebRequest request) {
        return handle(exception, request, ErrorCode.INVALID_INPUT);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleDefault(RuntimeException exception, WebRequest request) {
        return handle(exception, request, ErrorCode.UNEXPECTED);
    }
}
