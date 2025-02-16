package com.mskim.demo.base.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum VuejsExceptionType {
    invalid_request(HttpStatus.BAD_REQUEST, "invalid_request", "invalid_request", "A0000"),
    server_error(HttpStatus.INTERNAL_SERVER_ERROR, "internal_server_error", "internal_server_error", "A0500");

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
