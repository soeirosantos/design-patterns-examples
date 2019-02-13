package com.nytimes.pubp.articles.service.flow;

import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;

public interface PublishStep {

    void execute(PublishContext context) throws PublishException;
}
