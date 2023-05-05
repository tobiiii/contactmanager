package com.ps.contactmanager.domain.enums;

public enum ERROR_CODE {
    EMAIL_EXISTS("EMAIL_EXIST"),
    INEXISTANT_PRIVILEGE("INEXISTANT_PRIVILEGE"),
    ATTRIBUTED_PROFILE("ATTRIBUTED_PROFILE"),
    NAME_EXISTS("NAME_EXISTS"),
    CODE_EXISTS("CODE_EXISTS"),
    INEXISTANT_PROFILE("INEXISTANT_PROFILE"),
    EMPTY_PRIVILEGES_LIST("EMPTY_PRIVILEGES_LIST");

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
