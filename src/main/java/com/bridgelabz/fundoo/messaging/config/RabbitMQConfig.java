package com.bridgelabz.fundoo.messaging.config;

import com.bridgelabz.fundoo.messaging.constants.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        name = "messaging.provider",
        havingValue = "rabbitmq",
        matchIfMissing = true
)
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
    public Queue reminderQueue() {
        return new Queue(RabbitMQConstants.REMINDER_QUEUE);
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

    @Bean
    public Binding reminderBinding() {
        return BindingBuilder.bind(reminderQueue())
                .to(exchange())
                .with(RabbitMQConstants.REMINDER_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
