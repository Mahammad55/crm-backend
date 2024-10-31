package com.project.crmbackend.controller;

import com.project.crmbackend.dto.request.CustomerRequest;
import com.project.crmbackend.dto.response.CustomerResponse;
import com.project.crmbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createUser(@Validated @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.status(CREATED).body(customerService.createCustomer(customerRequest));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomer() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }
}
