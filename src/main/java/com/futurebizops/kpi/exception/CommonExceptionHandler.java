package com.futurebizops.kpi.exception;

import com.futurebizops.kpi.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(KPIException.class)
    public final ResponseEntity<Object> handlePMException(KPIException exception, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder().sourceClass(exception.getSourceClass()).isSuccess(exception.isSuccess()).details(exception.getMessage()).build();
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}
