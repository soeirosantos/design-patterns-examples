package com.nytimes.pubp.articles.service.flow.impl.steps;

import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.PublishStep;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;

import java.util.UUID;

public class SetUri implements PublishStep {

    private static final String NYT_ARTICLE_SCHEME_PATH = "nyt://article/";

    private PublishStep next;

    public SetUri() {
    }

    public SetUri(PublishStep next) {
        this.next = next;
    }

    @Override
    public void executeNext(PublishContext context) throws PublishException {
        context.getArticle().setUri(NYT_ARTICLE_SCHEME_PATH + UUID.randomUUID().toString());
    }

    @Override
    public PublishStep getNext() {
        return next;
    }
}
