package com.jokerconf.gateway.mvc.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GatewayResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemDetail body = this.createProblemDetail(ex, status, "Internal server error", (String)null, (Object[])null, request);
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        logger.error("@ControllerAdvice worked!");
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
