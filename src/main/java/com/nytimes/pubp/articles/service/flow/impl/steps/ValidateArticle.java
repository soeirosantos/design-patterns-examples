package com.nytimes.pubp.articles.service.flow.impl.steps;

import com.nytimes.pubp.articles.service.exception.InvalidArticleException;
import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.PublishStep;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;

public class ValidateArticle implements PublishStep {

    private PublishStep next;

    public ValidateArticle() {
    }

    public ValidateArticle(PublishStep next) {
        this.next = next;
    }

    @Override
    public void execute(PublishContext context) throws PublishException {
        if (context.hasErrors()) {
            throw new InvalidArticleException(context.getErrors());
        }
        executeNext(context);
    }

    @Override
    public PublishStep getNext() {
        return next;
    }
}
