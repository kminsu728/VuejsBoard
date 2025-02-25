package com.mskim.demo.rest.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum QueueMessageType {
    CREATE_POST,
    DELETE_POST,
    UPDATE_POST,
    CREATE_BOARD,
    ADD_COMMENT,
    DELETE_COMMENT,
    INCREASE_VIEWS;
}
