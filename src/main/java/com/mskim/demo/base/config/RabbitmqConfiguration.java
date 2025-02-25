package com.mskim.demo.base.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "services.rabbitmq")
@RequiredArgsConstructor
public class RabbitmqConfiguration {
    private String host;

    private int port;

    private String username;

    private String password;

    private String exchangeName;

    private String queueName;

    private String routingKey;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public DirectExchange vueDirectExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue vueQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding bindingCreate(DirectExchange vueDirectExchange, Queue vueQueue) {
        return BindingBuilder.bind(vueQueue).to(vueDirectExchange).with(routingKey);
    }

}
