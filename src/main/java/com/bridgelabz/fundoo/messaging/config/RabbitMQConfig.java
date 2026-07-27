package com.bridgelabz.fundoo.messaging.config;


import com.bridgelabz.fundoo.messaging.constants.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(RabbitMQConstants.EXCHANGE);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(RabbitMQConstants.USER_QUEUE);
    }

    @Bean
    public Queue passwordQueue() {
        return new Queue(RabbitMQConstants.PASSWORD_QUEUE);
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder.bind(userQueue())
                .to(exchange())
                .with(RabbitMQConstants.USER_ROUTING_KEY);
    }

    @Bean
    public Binding passwordBinding() {
        return BindingBuilder.bind(passwordQueue())
                .to(exchange())
                .with(RabbitMQConstants.PASSWORD_ROUTING_KEY);
    }
}
