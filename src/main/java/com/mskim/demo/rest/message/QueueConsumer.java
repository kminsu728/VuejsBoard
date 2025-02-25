package com.mskim.demo.rest.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueConsumer {

    @RabbitListener(queues = "vue.queue")
    public void receive(String message) {
        QueueMessageType messageType = QueueMessageType.valueOf(message.toUpperCase());

        switch (messageType) {
            case CREATE_POST:
                log.info("Create post");
                break;
            case DELETE_POST:
                log.info("Create post");
                break;
            case UPDATE_POST:
                log.info("Create post");
                break;
            case ADD_COMMENT:
                log.info("Create comment");
                break;
            case DELETE_COMMENT:
                log.info("Create comment");
                break;
            case CREATE_BOARD:
                log.info("Create board");
                break;
            case INCREASE_VIEWS:
                log.info("Increase views");
                break;
            default:
                log.error("Unknown message type: {}", messageType);
                break;

        }
    }

}
