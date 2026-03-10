package com.basic.dev.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class DefaultExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorObject> handleBusinessException(BusinessException ex) {
        ErrorObject errorObject = ErrorObject.builder()
                .status(ex.getHttpStatus().value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(ex.getHttpStatus()).body(errorObject);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorObject errorObject = ErrorObject.builder()
                .status(400)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(errorObject);
    }
}