package com.mskim.demo.rest.message;

import com.mskim.demo.base.config.RabbitmqConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.AmqpTimeoutException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitmqConfiguration rabbitmqConfiguration;

    public void sentToQueue(QueueMessageType queueMessageType, Object args) {
        try {
            QueueMessage message = new QueueMessage(queueMessageType, args);
            rabbitTemplate.convertAndSend(rabbitmqConfiguration.getExchangeName(),
                    rabbitmqConfiguration.getRoutingKey(),
                    message);
        } catch (AmqpConnectException e) {
            log.error("RabbitMQ connect fail - retry required: {}", e.getMessage(), e);
        } catch (AmqpTimeoutException e) {
            log.error("RabbitMQ timeout - Network status check required: {}", e.getMessage(), e);
        } catch (MessageConversionException e) {
            log.error("message conversion failed - due to serialization error.: {}", e.getMessage(), e);
        } catch (AmqpException e) {
            log.error("RabbitMQ send fail: {}", e.getMessage(), e);
        }
    }
}
