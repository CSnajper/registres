package com.in4mo.registers.rest.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsufficientFundsException extends RuntimeException {
    private String fromRegistry;
    private Long amount;
}
