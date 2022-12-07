package com.assessment.college_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionController {
    @ExceptionHandler(value = {Exception.class})
    public ApiErrorResponse handle(Exception ex, WebRequest request) {
        return new ApiErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
