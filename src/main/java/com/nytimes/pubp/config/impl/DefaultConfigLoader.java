package com.nytimes.pubp.config.impl;

import com.nytimes.pubp.config.AppConfigLoader;

import java.util.Optional;

public class DefaultConfigLoader implements AppConfigLoader {

    private static final String BASE_URL = "http://localhost:8080/publisher";

    @Override
    public AppConfig load() {
        return AppConfig.AppConfigBuilder.builder()
                .withEnvironment(AppConfig.Environment.LOCAL)
                .withSendGridApiKey(System.getenv("SENDGRID_API_KEY"))
                .withGatewayHost(System.getenv("GATEWAY_HOST"))
                .withGatewayPort(Optional.ofNullable(System.getenv("GATEWAY_PORT"))
                        .map(Integer::parseInt).orElseThrow())
                .withGatewayRootCaPath(System.getenv("ROOT_CA_PATH"))
                .withGatewayCredentialsPath(System.getenv("CREDENTIALS_PATH"))
                .withDatastoreProjectId(System.getenv("DATASTORE_PROJECT_ID"))
                .withBaseUrl(BASE_URL)
                .build();
    }
}
