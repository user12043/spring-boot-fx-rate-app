package com.user12043.fxrate.config;

import com.user12043.fxrate.dto.exception.FxRateExceptionResponse;
import com.user12043.fxrate.util.FxRateExceptionUtil;
import com.user12043.fxrate.util.FxRateExceptionUtil.ErrorCode;
import com.user12043.fxrate.util.exception.ExchangeAPIConnectionFailException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Configures global error handling mechanism for the endpoints
 * by @{@link ControllerAdvice}
 */
@ControllerAdvice
public class FxRateExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Generic handle method includes common call
     *
     * @param exception Exception thrown by the service
     * @param request   Corresponding web request
     * @param errorCode Custom error code given for the case
     * @return Final response for the exception
     */
    private ResponseEntity<?> handle(RuntimeException exception, WebRequest request, ErrorCode errorCode) {
        return handleExceptionInternal(
                exception,
                new FxRateExceptionResponse(errorCode, FxRateExceptionUtil.getMessage(errorCode), exception),
                new HttpHeaders(),
                FxRateExceptionUtil.getHttpStatus(errorCode),
                request);
    }

    /**
     * Handles the connection errors when connecting to the external API
     *
     * @param exception Exception thrown by the service
     * @param request   Corresponding web request
     * @return Final response for the exception
     */
    @ExceptionHandler(value = {ExchangeAPIConnectionFailException.class, HttpClientErrorException.class})
    private ResponseEntity<?> handleInternal(RuntimeException exception, WebRequest request) {
        if (exception instanceof HttpClientErrorException && ((HttpClientErrorException) exception).getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            return handle(exception, request, ErrorCode.EXCHANGE_API_FORBIDDEN);
        }
        return handle(exception, request, ErrorCode.EXCHANGE_API_CONNECTION_FAIL);
    }


    /**
     * Handles the case when there is no data found for given parameters
     *
     * @param exception Exception thrown by the service
     * @param request   Corresponding web request
     * @return Final response for the exception
     */
    @ExceptionHandler(value = {EmptyResultDataAccessException.class, NoSuchElementException.class})
    public ResponseEntity<?> handleNoData(RuntimeException exception, WebRequest request) {
        return handle(exception, request, ErrorCode.NO_DATA);
    }

    /**
     * Handles the case when invalid parameters supplied for any service
     *
     * @param exception Exception thrown by the service
     * @param request   Corresponding web request
     * @return Final response for the exception
     */
    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleIllegalArgument(RuntimeException exception, WebRequest request) {
        return handle(exception, request, ErrorCode.INVALID_INPUT);
    }

    /**
     * Handles any case which couldn't been handled by other handlers
     *
     * @param exception Exception thrown by the service
     * @param request   Corresponding web request
     * @return Final response for the exception
     */
    @ExceptionHandler
    public ResponseEntity<?> handleDefault(RuntimeException exception, WebRequest request) {
        return handle(exception, request, ErrorCode.UNEXPECTED);
    }
}
