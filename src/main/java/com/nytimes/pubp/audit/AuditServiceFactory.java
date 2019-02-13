package com.nytimes.pubp.audit;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.nytimes.pubp.config.impl.AppConfig;
import com.nytimes.pubp.audit.impl.DefaultAuditService;

import java.util.Optional;

public class AuditServiceFactory {

    public static AuditService create(AppConfig config) {
        Datastore datastore;
        if (Optional.ofNullable(config.getDatastoreProjectId()).isPresent()) {
            DatastoreOptions.Builder datastoreBuilder = DatastoreOptions
                    .newBuilder()
                    .setProjectId(config.getDatastoreProjectId())
                    .setCredentials(DatastoreOptions.getDefaultInstance().getCredentials())
                    .setRetrySettings(DatastoreOptions
                            .getDefaultRetrySettings()
                            .toBuilder()
                            .setMaxAttempts(3).build())
                    .setTransportOptions(DatastoreOptions
                            .getDefaultHttpTransportOptions()
                            .toBuilder()
                            .setReadTimeout(1000)
                            .setConnectTimeout(1000).build());
            datastore = datastoreBuilder.build().getService();
        } else {
            datastore = DatastoreOptions.getDefaultInstance().getService();
        }

        return new DefaultAuditService(datastore);
    }

}
