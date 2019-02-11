package com.nytimes.pubp.gateway;

import com.nytimes.pubp.articles.domain.Article;
import com.nytimes.pubp.gateway.exception.GatewayClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayClient.class);

    private final String host;
    private final int port;
    private final String rootCa;
    private final String credentialsPath;

    public GatewayClient(String host, int port, String rootCa, String credentialsPath) {
        this.host = host;
        this.port = port;
        this.rootCa = rootCa;
        this.credentialsPath = credentialsPath;
    }

    public void publish(Article article) throws GatewayClientException {
        LOGGER.info("Publish {}", article.getUri());
    }

    public void unpublish(Article article) throws GatewayClientException {
        LOGGER.info("Unpublish {}", article.getUri());
    }

    public void preview(Article article) throws GatewayClientException {
        LOGGER.info("Preview {}", article.getUri());
    }
}
