package com.project.crmbackend.service.impl;

import com.project.crmbackend.dto.request.CustomerRequest;
import com.project.crmbackend.dto.response.CustomerResponse;
import com.project.crmbackend.mapper.CustomerMapper;
import com.project.crmbackend.repository.CustomerRepository;
import com.project.crmbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        return customerMapper.entityToResponse(customerRepository.save(customerMapper.requestToEntity(customerRequest)));
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        return customerRepository.findAll().stream()
                .map(customerMapper::entityToResponse)
                .toList();
    }
}
