package com.bridgelabz.fundoo.messaging.constants;

public final class RabbitMQConstants {

    private RabbitMQConstants(){}

    public static final String EXCHANGE = "fundoo.exchange";

    public static final String USER_QUEUE = "fundoo.user.queue";

    public static final String PASSWORD_QUEUE = "fundoo.password.queue";

    public static final String REMINDER_QUEUE = "fundoo.reminder.queue";

    public static final String USER_ROUTING_KEY = "user.register";

    public static final String PASSWORD_ROUTING_KEY = "password.reset";

    public static final String REMINDER_ROUTING_KEY = "reminder.alert";
}
