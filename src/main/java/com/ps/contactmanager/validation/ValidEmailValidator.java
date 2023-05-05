package com.ps.contactmanager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    private boolean optional;

    public void initialize(ValidEmail target) {
        optional = target.optional();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return this.optional;
        }

        var pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        var matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
