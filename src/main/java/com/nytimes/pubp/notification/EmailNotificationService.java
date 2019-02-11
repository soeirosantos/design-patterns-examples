package com.nytimes.pubp.notification;

import com.nytimes.pubp.notification.exception.NotificationException;
import com.sendgrid.SendGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationService.class);

    private final SendGrid sendGrid;

    public EmailNotificationService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public void notify(String uri, String service) throws NotificationException {

        LOGGER.info("Sending email notification {} {}", uri, service);
    }
}
