package com.project.crmbackend.validation;

import com.project.crmbackend.annotation.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidation implements ConstraintValidator<ValidPhoneNumber, String> {
    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber.isBlank()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Phone number must not be empty").addConstraintViolation();
            return false;
        }

        if (phoneNumber.length() == 12 && phoneNumber.matches("^(994)(50|51|55|70|77|99).*$")) {
            return true;
        } else if (phoneNumber.length() == 10 && phoneNumber.matches("^(0)(50|51|55|70|77|99).*$")) {
            return true;
        } else if (phoneNumber.length() == 9 && phoneNumber.matches("^(50|51|55|70|77|99).*$")) {
            return true;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Phone number must be 9, 10, or 12 digits long and start with a valid code").addConstraintViolation();
            return false;
        }
    }
}
