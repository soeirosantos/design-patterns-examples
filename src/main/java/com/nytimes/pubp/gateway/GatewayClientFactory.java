package com.nytimes.pubp.gateway;

import com.nytimes.pubp.config.impl.AppConfig;
import com.nytimes.pubp.gateway.impl.GatewayClient;

public class GatewayClientFactory {

    public static GatewayClient create(AppConfig config) {
        String gatewayHost = config.getGatewayHost();
        Integer gatewayPort = config.getGatewayPort();
        String gatewayRootCaPath = config.getGatewayRootCaPath();
        String gatewayCredentialsPath = config.getGatewayCredentialsPath();

        return new GatewayClient(gatewayHost, gatewayPort, gatewayRootCaPath, gatewayCredentialsPath);
    }

}
