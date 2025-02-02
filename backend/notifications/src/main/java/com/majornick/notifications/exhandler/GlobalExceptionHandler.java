package com.majornick.notifications.exhandler;

import com.majornick.notifications.exception.CustomerNotFoundException;
import com.majornick.notifications.exception.EmptyLoginAttemptException;
import com.majornick.notifications.exception.InvalidLoginAttemptException;
import com.majornick.notifications.exception.UsernameAlreadyExistsException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomerNotFoundException.class,/* UsernameNotFoundException.class*/})
    public ResponseEntity<String> handleNotFound(RuntimeException exp) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exp.getMessage());
    }

    @ExceptionHandler({AuthenticationException.class
            , UsernameAlreadyExistsException.class
            , EmptyLoginAttemptException.class
            , InvalidLoginAttemptException.class})
    public ResponseEntity<String> handleBadRequest(RuntimeException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Map<String, String> errors = new HashMap<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtToken(ExpiredJwtException exp) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exp.getMessage());
    }
}
