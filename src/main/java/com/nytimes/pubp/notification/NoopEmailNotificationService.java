package com.nytimes.pubp.notification;

import com.nytimes.pubp.notification.exception.NotificationException;

public class NoopEmailNotificationService implements EmailNotificationService {

    @Override
    public void notify(String uri, String service) throws NotificationException {
        //noop
    }
}
