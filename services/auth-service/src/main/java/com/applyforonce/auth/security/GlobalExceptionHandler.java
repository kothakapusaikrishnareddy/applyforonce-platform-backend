package com.applyforonce.auth.security;

import com.applyforonce.auth.exception.EmailAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.applyforonce.auth.dto.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse>
        handleEmailAlreadyExists(EmailAlreadyExistsException ex) {

        log.warn("Registration rejected: duplicate email");

        ErrorResponse response = new ErrorResponse(
            Instant.now(),
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            ex.getMessage(),
            Map.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);


    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
        handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        Map<String, String> validationErrors =
            ex.getBindingResult()
                    .getFieldErrors()
                        .stream()
                            .collect(Collectors.toMap(
                                error -> error.getField(),
                                error -> error.getDefaultMessage() ==null
                                ? "Invalid value" : error.getDefaultMessage(),
                                (first,second) -> first

                                ));


        log.warn("Registration rejected: invalid data");

        ErrorResponse response = new ErrorResponse(
            Instant.now(),
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            "Request validation failed",
            validationErrors
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
        handleException(Exception ex) {

        log.error("Unexpected application error", ex);

        ErrorResponse response = new ErrorResponse(
            Instant.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "An unexpected error occurred",
            Map.of()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response);
    }


}
