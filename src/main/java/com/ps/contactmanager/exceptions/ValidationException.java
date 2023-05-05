package com.ps.contactmanager.exceptions;

import com.ps.contactmanager.domain.enums.ERROR_CODE;

public class ValidationException extends RuntimeException {
    public ValidationException(ERROR_CODE message) {
        super(message.getMessage());
    }
}