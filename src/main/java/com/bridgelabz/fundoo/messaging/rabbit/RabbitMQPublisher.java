package com.bridgelabz.fundoo.messaging.rabbit;


import com.bridgelabz.fundoo.messaging.constants.RabbitMQConstants;
import com.bridgelabz.fundoo.messaging.event.PasswordResetEvent;
import com.bridgelabz.fundoo.messaging.event.UserRegisteredEvent;
import com.bridgelabz.fundoo.messaging.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(
        name = "messaging.provider",
        havingValue = "rabbit",
        matchIfMissing = true
)
public class RabbitMQPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishUserRegistered(UserRegisteredEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.EXCHANGE,
                RabbitMQConstants.USER_ROUTING_KEY,
                event
        );

        log.info("Published UserRegisteredEvent {}", event.getEmail());
    }

    @Override
    public void publishPasswordReset(PasswordResetEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.EXCHANGE,
                RabbitMQConstants.PASSWORD_ROUTING_KEY,
                event
        );

        log.info("Published PasswordResetEvent {}", event.getEmail());
    }
}