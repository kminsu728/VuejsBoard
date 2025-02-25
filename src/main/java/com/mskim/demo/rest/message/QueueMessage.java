package com.mskim.demo.rest.message;

import lombok.Getter;

import java.io.Serializable;

public class QueueMessage implements Serializable {
    @Getter
    QueueMessageType queueMessageType;

    @Getter
    private Object args;

    public QueueMessage(QueueMessageType queueMessageType, Object args) {
        this.queueMessageType = queueMessageType;
        this.args = args;
    }
}
