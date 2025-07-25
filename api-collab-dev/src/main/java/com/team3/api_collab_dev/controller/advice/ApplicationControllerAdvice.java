package com.team3.api_collab_dev.controller.advice;


import com.team3.api_collab_dev.Exception.ExistSameEmailException;
import com.team3.api_collab_dev.Exception.IncorrectEmailException;
import com.team3.api_collab_dev.Exception.IncorrectPasswordException;
import com.team3.api_collab_dev.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException except,
            HttpServletRequest request) {
        String message = except.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                .orElse("Validation error");

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler({
            RuntimeException.class,
            EntityNotFoundException.class,
            ExistSameEmailException.class,
            IncorrectEmailException.class,
            IncorrectPasswordException.class,
            SecurityException.class})
    public @ResponseBody ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException except,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                LocalDateTime.now(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                except.getMessage(),
                request.getRequestURI()

        ));

    }
}
