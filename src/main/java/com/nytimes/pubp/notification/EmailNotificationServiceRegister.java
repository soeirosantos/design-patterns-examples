package com.nytimes.pubp.notification;

import com.nytimes.pubp.config.impl.AppConfig;
import com.nytimes.pubp.notification.impl.NoopEmailNotificationService;
import com.nytimes.pubp.notification.impl.SendGridNotificationService;

import java.util.HashMap;
import java.util.Map;

public final class EmailNotificationServiceRegister {

    public enum EmailNotificationType {SEND_GRID, NOOP}

    private final Map<EmailNotificationType, EmailNotificationService> notificationServices = new HashMap<>();

    public EmailNotificationServiceRegister(AppConfig config) {
        notificationServices.put(EmailNotificationType.SEND_GRID, SendGridNotificationService.create(config));
        notificationServices.put(EmailNotificationType.NOOP, new NoopEmailNotificationService());
    }

    public EmailNotificationService lookup(EmailNotificationType type) {
        return notificationServices.get(type);
    }
}
