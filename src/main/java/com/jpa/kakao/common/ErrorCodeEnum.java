package com.jpa.kakao.common;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

    // 500
    SERVER_ERROR(500,"S001","Server Error Occur");

    private final String code;
    private final String message;
    private int status;

    ErrorCodeEnum(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
