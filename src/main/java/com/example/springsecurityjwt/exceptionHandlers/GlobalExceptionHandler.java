package com.example.springsecurityjwt.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.security.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> iAmATeapotExceptionHandler(ResponseStatusException e){
        return new ResponseEntity<>(e.getMessage(),e.getStatusCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> signatureExceptionHandler(RuntimeException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
