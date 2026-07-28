package com.bridgelabz.fundoo.messaging.consumer;

import com.bridgelabz.fundoo.messaging.constants.RabbitMQConstants;
import com.bridgelabz.fundoo.messaging.event.PasswordResetEvent;
import com.bridgelabz.fundoo.messaging.event.ReminderAlertEvent;
import com.bridgelabz.fundoo.messaging.event.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(
        name = "messaging.provider",
        havingValue = "rabbitmq",
        matchIfMissing = true
)
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

    @RabbitListener(queues = RabbitMQConstants.REMINDER_QUEUE)
    public void receiveReminderAlert(ReminderAlertEvent event) {
        log.info("Sending reminder alert notification to {} for note: {}", event.getOwnerEmail(), event.getTitle());
    }
}