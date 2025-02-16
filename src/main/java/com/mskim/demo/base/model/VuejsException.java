package com.mskim.demo.base.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class VuejsException extends RuntimeException {
    @Getter
    VuejsExceptionType exceptionType;
    @Getter
    final HttpStatus status;
    @Getter
    final String title;
    @Getter
    final String description;
    @Getter
    final String errorCode;

    public VuejsException(VuejsExceptionType exceptionType,
                          HttpStatus status,
                          String title,
                          String description,
                          String errorCode) {
        this.exceptionType = exceptionType;
        this.status = status;
        this.title = title;
        this.description = description;
        this.errorCode = errorCode;
    }

    public VuejsException(VuejsExceptionType exceptionType, String description) {
        this(exceptionType,
                exceptionType.getStatus(),
                exceptionType.getTitle(),
                description,
                exceptionType.getErrorCode());
    }

    public VuejsException(VuejsExceptionType exceptionType) {
        this(exceptionType,
                exceptionType.getStatus(),
                exceptionType.getTitle(),
                exceptionType.getDescription(),
                exceptionType.getErrorCode());
    }



}
