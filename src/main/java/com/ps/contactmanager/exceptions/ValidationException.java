package com.ps.contactmanager.exceptions;

import com.ps.contactmanager.domain.enums.ERROR_CODE;

public class ValidationException extends ContactmanagerException {
    private static final long serialVersionUID = 1L;

    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(ERROR_CODE errorCode) {
        super(errorCode.getValue());
        this.errorCode = errorCode;
    }

}