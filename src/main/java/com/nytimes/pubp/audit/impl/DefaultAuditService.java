package com.nytimes.pubp.audit.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.nytimes.pubp.audit.AuditService;
import com.nytimes.pubp.audit.exception.AuditException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DefaultAuditService implements AuditService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuditService.class);
    private static final String KIND = "ArticlePublisherAudit";

    private final Datastore datastore;

    public DefaultAuditService(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void log(String uri, String user, String service) throws AuditException {
        LocalDateTime now = LocalDateTime.now();

        String logMessage = String.format("Logging %s for %s by %s at %s", service, uri, user, now);

        String name = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Key taskKey = datastore.newKeyFactory().setKind(KIND).newKey(name);
        Entity task = Entity.newBuilder(taskKey).set("entry", logMessage).build();
        datastore.put(task);

        LOGGER.info(logMessage);
    }
}
