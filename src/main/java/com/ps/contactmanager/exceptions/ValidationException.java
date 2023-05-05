package com.ps.contactmanager.exceptions;

import com.ps.contactmanager.domain.enums.ERROR_CODE;

public class ValidationException extends ContactmanagerException {
    public ValidationException(ERROR_CODE message) {
        super(message.getMessage());
    }
}