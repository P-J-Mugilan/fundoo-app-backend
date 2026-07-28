package com.bridgelabz.fundoo.messaging.kafka;

import com.bridgelabz.fundoo.messaging.event.PasswordResetEvent;
import com.bridgelabz.fundoo.messaging.event.ReminderAlertEvent;
import com.bridgelabz.fundoo.messaging.event.UserRegisteredEvent;
import com.bridgelabz.fundoo.messaging.publisher.EventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(
        name = "messaging.provider",
        havingValue = "kafka"
)
public class KafkaPublisher implements EventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publishUserRegistered(UserRegisteredEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user-events", String.valueOf(event.getUserId()), message);
            log.info("Published UserRegisteredEvent to Kafka: {}", event.getEmail());

            // Log to audit log topic as well
            String auditMessage = String.format("{\"eventType\": \"USER_REGISTRATION\", \"details\": \"User %s registered successfully\"}", event.getEmail());
            kafkaTemplate.send("audit-logs", String.valueOf(event.getUserId()), auditMessage);
        } catch (Exception e) {
            log.error("Failed to publish UserRegisteredEvent to Kafka: {}", e.getMessage());
        }
    }

    @Override
    public void publishPasswordReset(PasswordResetEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user-events", event.getEmail(), message);
            log.info("Published PasswordResetEvent to Kafka: {}", event.getEmail());
        } catch (Exception e) {
            log.error("Failed to publish PasswordResetEvent to Kafka: {}", e.getMessage());
        }
    }

    @Override
    public void publishReminderAlert(ReminderAlertEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("reminder-alerts", event.getOwnerEmail(), message);
            log.info("Published ReminderAlertEvent to Kafka: {}", event.getOwnerEmail());
        } catch (Exception e) {
            log.error("Failed to publish ReminderAlertEvent to Kafka: {}", e.getMessage());
        }
    }
}
