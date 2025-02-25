package com.mskim.demo.rest.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueConsumer {

    @RabbitListener(queues = "${services.rabbitmq.queue-name}")
    public void receive(QueueMessage message) {
        QueueMessageType messageType = message.getQueueMessageType();

        switch (messageType) {
            case CREATE_POST:
                log.info("CREATE_POST");
                break;
            case DELETE_POST:
                log.info("DELETE_POST");
                break;
            case UPDATE_POST:
                log.info("UPDATE_POST");
                break;
            case ADD_COMMENT:
                log.info("ADD_COMMENT");
                break;
            case DELETE_COMMENT:
                log.info("DELETE_COMMENT");
                break;
            case CREATE_BOARD:
                log.info("CREATE_BOARD");
                break;
            case INCREASE_VIEWS:
                log.info("INCREASE_VIEWS");
                break;
            default:
                log.error("Unknown message type: {}", messageType);
                break;

        }
    }

}
