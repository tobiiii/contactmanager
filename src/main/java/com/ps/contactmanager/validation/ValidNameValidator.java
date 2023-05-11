package com.ps.contactmanager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidNameValidator implements ConstraintValidator<ValidName, String> {

    private boolean optional = false;

    @Override
    public void initialize(ValidName constraintAnnotation) {
        optional = constraintAnnotation.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return optional;
        }

        var pattern = Pattern.compile("^[a-zA-ZÀ-ÿ\\-.æœ ]{1,50}$");
        var matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
