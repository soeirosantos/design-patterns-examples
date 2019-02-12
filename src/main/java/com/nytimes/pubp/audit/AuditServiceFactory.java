package com.nytimes.pubp.audit;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.nytimes.pubp.audit.impl.DefaultAuditService;

import java.util.Optional;

public class AuditServiceFactory {

    public static AuditService create() {
        Datastore datastore;
        if (Optional.ofNullable(System.getenv("DATASTORE_PROJECT_ID")).isPresent()) {
            DatastoreOptions.Builder datastoreBuilder = DatastoreOptions
                    .newBuilder()
                    .setProjectId(System.getenv("DATASTORE_PROJECT_ID"))
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
