package com.nytimes.pubp.notification;

import com.nytimes.pubp.notification.exception.NotificationException;

public interface EmailNotificationService {

    void notify(String uri, String service) throws NotificationException;
}
