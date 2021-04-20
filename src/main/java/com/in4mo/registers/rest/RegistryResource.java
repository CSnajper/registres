package com.in4mo.registers.rest;

import com.in4mo.registers.rest.dto.RegistryDto;
import com.in4mo.registers.rest.dto.RegistryRechargeDto;
import com.in4mo.registers.rest.dto.RegistryTransferDto;
import com.in4mo.registers.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController("/registry")
public class RegistryResource {

    @Autowired
    private RegistryService registryService;

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String home() {
        return "OK";
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void recharge(@RequestBody @Valid RegistryRechargeDto registryRechargeDto) {
        registryService.recharge(registryRechargeDto);
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void transfer(@RequestBody RegistryTransferDto registryTransferDto) {
        registryService.transfer(registryTransferDto);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegistryDto>> list() {
        return new ResponseEntity<>(registryService.list(), HttpStatus.OK);
    }
}
