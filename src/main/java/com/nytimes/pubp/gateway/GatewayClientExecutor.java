package com.nytimes.pubp.gateway;

import com.nytimes.pubp.articles.domain.Article;
import com.nytimes.pubp.gateway.exception.GatewayClientException;

public interface GatewayClientExecutor {
    void execute(Article article) throws GatewayClientException;
}
