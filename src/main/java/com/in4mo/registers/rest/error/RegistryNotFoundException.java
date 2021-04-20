package com.in4mo.registers.rest.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistryNotFoundException extends RuntimeException {
    private String registry;
}
