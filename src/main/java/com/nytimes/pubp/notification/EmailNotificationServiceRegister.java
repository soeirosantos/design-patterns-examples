package com.nytimes.pubp.notification;

import java.util.HashMap;
import java.util.Map;

public class EmailNotificationServiceRegister {

    public enum EmailNotificationType {SEND_GRID, NOOP}

    private Map<EmailNotificationType, EmailNotificationService> notificationServices = new HashMap<>();

    {
        notificationServices.put(EmailNotificationType.SEND_GRID, SendGridNotificationService.create());
        notificationServices.put(EmailNotificationType.NOOP, new NoopEmailNotificationService());
    }

    public EmailNotificationService lookup(EmailNotificationType type) {
        return notificationServices.get(type);
    }
}
