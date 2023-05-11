package com.ps.contactmanager.utils;

import lombok.*;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class JsonResponse {

    public enum STATUS {
        SUCCESS, FAILED
    }

    private String status;
    private String errorCode;
    private String errorMsg;
    private Object data;

    public JsonResponse(String errorCode, String errorMsg) {
        this.status = STATUS.FAILED.name();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public JsonResponse(final Object data) {
        this.status = STATUS.SUCCESS.name();
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatus(STATUS status) {
        if (status != null) {
            setStatus(status.name());
        }
    }

    public static class JsonResponseBuilder {
        public JsonResponseBuilder status(final STATUS status) {
            if (status != null) {
                this.status = status.name();
            }
            return this;
        }
    }
}
