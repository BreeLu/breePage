package com.springapp.breepage.core.utils;

public enum ErrorCode {
    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    FORBIDDEN(2, "forbidden");

    private final int code;

    private final String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode getErrorCode(int code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }

        return null;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
