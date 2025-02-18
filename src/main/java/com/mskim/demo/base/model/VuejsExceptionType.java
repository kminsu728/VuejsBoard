package com.mskim.demo.base.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum VuejsExceptionType {
    invalid_request(HttpStatus.BAD_REQUEST, "invalid_request", "invalid_request", "A0400"),
    server_error(HttpStatus.INTERNAL_SERVER_ERROR, "internal_server_error", "internal_server_error", "A0500"),
    data_initialize_fail(HttpStatus.BAD_REQUEST, "internal_server_error", "data_initialize_fail", "A1000"),
    login_fail(HttpStatus.BAD_REQUEST, "invalid_request", "login_fail", "A1001"),
    logout_fail(HttpStatus.BAD_REQUEST, "internal_server_error", "logout_fail", "A1002");

    private HttpStatus status;
    private String title;
    private String description;
    private String errorCode;

    VuejsExceptionType(HttpStatus status,
                       String title,
                       String description,
                       String errorCode) {
        this.status = status;
        this.title = title;
        this.description = description;
        this.errorCode = errorCode;
    }
}
