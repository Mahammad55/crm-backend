package com.project.crmbackend.service;

import com.project.crmbackend.dto.request.CustomerRequest;
import com.project.crmbackend.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);

    List<CustomerResponse> getAllCustomer();
}
