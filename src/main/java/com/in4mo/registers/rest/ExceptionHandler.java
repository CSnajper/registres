package com.in4mo.registers.rest;

import com.in4mo.registers.rest.error.InsufficientFundsException;
import com.in4mo.registers.rest.error.RegistryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(RegistryNotFoundException.class)
    public ResponseEntity<String> handleRegistryNotFoundException(RegistryNotFoundException e) {

        return new ResponseEntity<>(String.format("Registry with name: '%s' was not found", e.getRegistry()), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException e) {

        return new ResponseEntity<>(String.format("Insufficient Funds (%d) for registry: %s", e.getAmount(), e.getFromRegistry()), HttpStatus.BAD_REQUEST);
    }
}