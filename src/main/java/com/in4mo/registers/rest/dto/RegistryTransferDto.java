package com.in4mo.registers.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryTransferDto {
    @NotEmpty
    private String fromRegistry;
    @NotEmpty
    private String toRegistry;
    @NotNull
    private Long amount;
}
