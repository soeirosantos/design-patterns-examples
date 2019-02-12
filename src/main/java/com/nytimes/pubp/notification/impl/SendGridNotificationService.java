package com.nytimes.pubp.notification.impl;

import com.nytimes.pubp.notification.EmailNotificationService;
import com.nytimes.pubp.notification.exception.NotificationException;
import com.sendgrid.SendGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendGridNotificationService implements EmailNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendGridNotificationService.class);

    private final SendGrid sendGrid;

    public SendGridNotificationService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public void notify(String uri, String service) throws NotificationException {

        LOGGER.info("Sending email notification {} {}", uri, service);
    }

    public static EmailNotificationService create() {
        SendGrid sendGrid = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        return new SendGridNotificationService(sendGrid);
    }
}
