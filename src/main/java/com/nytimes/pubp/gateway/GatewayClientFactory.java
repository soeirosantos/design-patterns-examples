package com.nytimes.pubp.gateway;

import java.util.Optional;

public class GatewayClientFactory {

    public static GatewayClient create() {
        String gatewayHost = System.getenv("GATEWAY_HOST");
        Integer gatewayPort = Optional.ofNullable(System.getenv("GATEWAY_PORT"))
                .map(Integer::parseInt).orElseThrow();
        String gatewayRootCaPath = System.getenv("ROOT_CA_PATH");
        String gatewayCredentialsPath = System.getenv("CREDENTIALS_PATH");

        return new GatewayClient(gatewayHost, gatewayPort, gatewayRootCaPath,
                gatewayCredentialsPath);
    }

}
