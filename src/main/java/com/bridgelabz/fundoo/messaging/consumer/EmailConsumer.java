package com.bridgelabz.fundoo.messaging.consumer;


import com.bridgelabz.fundoo.messaging.constants.RabbitMQConstants;
import com.bridgelabz.fundoo.messaging.event.PasswordResetEvent;
import com.bridgelabz.fundoo.messaging.event.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailConsumer {

    @RabbitListener(queues = RabbitMQConstants.USER_QUEUE)
    public void receiveUserRegistration(UserRegisteredEvent event) {

        log.info("Sending welcome email to {}", event.getEmail());

        // emailService.sendWelcomeEmail(event);
    }

    @RabbitListener(queues = RabbitMQConstants.PASSWORD_QUEUE)
    public void receivePasswordReset(PasswordResetEvent event) {

        log.info("Sending password reset email to {}", event.getEmail());

        // emailService.sendPasswordReset(event);
    }
}