package com.project.crmbackend.dto.request;

import com.project.crmbackend.annotation.ValidPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String customerName;

    private String companyName;

    @ValidPhoneNumber
    private String phoneNumber;
}
