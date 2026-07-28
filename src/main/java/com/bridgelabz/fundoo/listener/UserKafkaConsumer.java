package com.bridgelabz.fundoo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@org.springframework.context.annotation.Profile("!test")
@ConditionalOnProperty(
        name = "messaging.provider",
        havingValue = "kafka"
)
public class UserKafkaConsumer {

    @KafkaListener(topics = "user-events", groupId = "fundoo-group")
    public void consumeUserEvents(String message) {
        log.info("Kafka Consumer: Received USER EVENT: {}", message);
    }

    @KafkaListener(topics = "reminder-alerts", groupId = "fundoo-group")
    public void consumeReminderAlerts(String message) {
        log.info("Kafka Consumer: Received REMINDER ALERT: {}", message);
    }

    @KafkaListener(topics = "audit-logs", groupId = "fundoo-group")
    public void consumeAuditLogs(String message) {
        log.info("Kafka Consumer: Received AUDIT LOG: {}", message);
    }
}
