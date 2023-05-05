package com.ps.contactmanager.domain.enums;

public enum ERROR_CODE {
    BAD_REQUEST("BAD_REQUEST"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    INEXISTANT_USER("INEXISTANT_USER"),
    EMAIL_EXISTS("EMAIL_EXIST"),
    DIFFERENT_TOKEN("DIFFERENT_TOKEN"),

    TOKEN_ALREADY_VALID("TOKEN_ALREADY_VALID"),

    INCORRECT_PASSWORD("INCORRECT_PASSWORD"),
    INCORRECT_EMAIL("INCORRECT_EMAIL"),
    INVALID_SESSION("INVALID_SESSION"),
    SESSION_RESERVED("SESSION_RESERVED"),
    INCORRECT_USERNAME_OR_PASSWORD("INCORRECT_USERNAME_OR_PASSWORD"),
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND"),
    EXPIRED_TOKEN("EXPIRED_TOKEN"),
    UNAUTHORIZED("UNAUTHORIZED"),
    INEXISTANT_PRIVILEGE("INEXISTANT_PRIVILEGE"),
    ATTRIBUTED_PROFILE("ATTRIBUTED_PROFILE"),
    INEXISTANT_PROFILE("INEXISTANT_PROFILE"),
    INEXISTANT_DOCTOR("INEXISTANT_DOCTOR"),
    INEXISTANT_APPOINTMENT("INEXISTANT_APPOINTMENT"),
    INEXISTANT_PRESCRIPTION("INEXISTANT_PRESCRIPTION"),
    INEXISTANT_MEDICAL_HISTORY("INEXISTANT_MEDICAL_HISTORY"),
    NAME_EXISTS("NAME_EXISTS"),
    CODE_EXISTS("CODE_EXISTS"),
    EMPTY_PRIVILEGES_LIST("EMPTY_PRIVILEGES_LIST"),
    INEXISTANT_PATIENT_PROFILE("INEXISTANT_PATIENT_PROFILE"),
    INEXISTANT_DOC_PROFILE("INEXISTANT_DOC_PROFILE"),
    REFERENCED_USER("REFERENCED_USER");

    private String value;

    ERROR_CODE(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }


    private final String message;

    ERROR_CODE(String message) {
        this.message = message;
    }

    public String getMessage() {
            return message;
    }

    public String getMessageWithErrorCode(Object[] params) {

            return name() + ":" + message;
    }

}
