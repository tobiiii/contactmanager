package com.ps.contactmanager.exceptions;

import com.ps.contactmanager.domain.enums.ERROR_CODE;

import java.text.MessageFormat;

public abstract class ContactmanagerException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    ERROR_CODE errorCode;

    public ContactmanagerException(ERROR_CODE error_code, String message) {
        super(message);
        errorCode = error_code;
    }

    public ContactmanagerException(String message) {
        super(message);
    }

    public ContactmanagerException(ERROR_CODE error_code, Exception e) {
        super(e);
        this.errorCode = error_code;
    }

    public String getErrorCode() {
        return errorCode.name();
    }


    public void setErrorCode(ERROR_CODE errorCode) {
        this.errorCode = errorCode;
    }

    public String formatMessage() {
        return super.getMessage();
    }

    public String formatMessageWithErrorCode() {
        return errorCode.name() + ":" + formatMessage();
    }

    @Override
    public String getMessage() {
        if (errorCode == null) {
            return super.getMessage();
        } else {
            return errorCode.getMessage();
        }
    }
}
