package com.mskim.demo.base.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitmqConfiguration {


    @Value("${services.rabbitmq.host}")
    private String host;

    @Value("${services.rabbitmq.port}")
    private int port;

    @Value("${services.rabbitmq.username}")
    private String username;

    @Value("${services.rabbitmq.password}")
    private String password;

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
        return new DirectExchange("vue.direct");
    }

    @Bean
    public Queue vueQueue() {
        return new Queue("vue.queue", true);
    }

    @Bean
    public Binding bindingCreate(DirectExchange vueDirectExchange, Queue vueQueue) {
        return BindingBuilder.bind(vueQueue).to(vueDirectExchange).with("vue.key");
    }

}
