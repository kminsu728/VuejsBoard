package com.mskim.demo.rest.message;

import lombok.Getter;

public class QueueMessage {
    @Getter
    QueueMessageType queueMessageType;

    @Getter
    private Object args;

    public QueueMessage(QueueMessageType queueMessageType, Object args) {
        this.queueMessageType = queueMessageType;
        this.args = args;
    }
}
