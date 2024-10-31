package com.project.crmbackend.dto.request;

import com.project.crmbackend.annotation.ValidPhoneNumber;
import com.project.crmbackend.enums.UserRole;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String firstname;

    private String lastname;

    @Email(message = "Email address must be valid")
    private String email;

    @ValidPhoneNumber
    private String phoneNumber;

    private String password;

    private UserRole userRole;
}
