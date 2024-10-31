package com.project.crmbackend.mapper;

import com.project.crmbackend.dto.request.CustomerRequest;
import com.project.crmbackend.dto.response.CustomerResponse;
import com.project.crmbackend.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer requestToEntity(CustomerRequest customerRequest);

    CustomerResponse entityToResponse(Customer customer);
}
