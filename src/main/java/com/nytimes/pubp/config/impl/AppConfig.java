package com.nytimes.pubp.config.impl;

public final class AppConfig {

    public enum Environment {LOCAL, DEV, STG, PRD}

    private String sendGridApiKey;
    private String gatewayHost;
    private Integer gatewayPort;
    private String gatewayRootCaPath;
    private String gatewayCredentialsPath;
    private String datastoreProjectId;
    private Environment environment;
    private String baseUrl;

    public Environment getEnvironment() {
        return environment;
    }

    public String getSendGridApiKey() {
        return sendGridApiKey;
    }

    public String getGatewayHost() {
        return gatewayHost;
    }

    public Integer getGatewayPort() {
        return gatewayPort;
    }

    public String getGatewayRootCaPath() {
        return gatewayRootCaPath;
    }

    public String getDatastoreProjectId() {
        return datastoreProjectId;
    }

    public String getGatewayCredentialsPath() {
        return gatewayCredentialsPath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    static class AppConfigBuilder {

        private String sendGridApiKey;
        private String gatewayHost;
        private Integer gatewayPort;
        private String gatewayRootCaPath;
        private String gatewayCredentialsPath;
        private String datastoreProjectId;
        private AppConfig.Environment environment;
        private String baseUrl;

        static AppConfigBuilder builder() {
            return new AppConfigBuilder();
        }

        AppConfigBuilder withSendGridApiKey(String sendGridApiKey) {
            this.sendGridApiKey = sendGridApiKey;
            return this;
        }

        AppConfigBuilder withGatewayHost(String gatewayHost) {
            this.gatewayHost = gatewayHost;
            return this;
        }

        AppConfigBuilder withGatewayPort(Integer gatewayPort) {
            this.gatewayPort = gatewayPort;
            return this;
        }

        AppConfigBuilder withGatewayRootCaPath(String gatewayRootCaPath) {
            this.gatewayRootCaPath = gatewayRootCaPath;
            return this;
        }

        AppConfigBuilder withDatastoreProjectId(String datastoreProjectId) {
            this.datastoreProjectId = datastoreProjectId;
            return this;
        }

        AppConfigBuilder withEnvironment(AppConfig.Environment environment) {
            this.environment = environment;
            return this;
        }

        AppConfigBuilder withGatewayCredentialsPath(String gatewayCredentialsPath) {
            this.gatewayCredentialsPath = gatewayCredentialsPath;
            return this;
        }

        AppConfigBuilder withBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        AppConfig build() {
            AppConfig config = new AppConfig();
            config.sendGridApiKey = sendGridApiKey;
            config.gatewayHost = gatewayHost;
            config.gatewayPort = gatewayPort;
            config.gatewayRootCaPath = gatewayRootCaPath;
            config.gatewayCredentialsPath = gatewayCredentialsPath;
            config.datastoreProjectId = datastoreProjectId;
            config.environment = environment;
            config.baseUrl = baseUrl;
            return config;
        }
    }
}
