package com.bridgelabz.fundoo.messaging.publisher;

import com.bridgelabz.fundoo.messaging.event.PasswordResetEvent;
import com.bridgelabz.fundoo.messaging.event.UserRegisteredEvent;

public interface EventPublisher {

    void publishUserRegistered(UserRegisteredEvent event);

    void publishPasswordReset(PasswordResetEvent event);
}