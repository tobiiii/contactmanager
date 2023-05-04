package com.ps.contactmanager.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private boolean optional;

    @Override
    public void initialize(ValidPassword target) {
        optional = target.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ContactmanagerValidation.validatePassword(value);
    }
}
