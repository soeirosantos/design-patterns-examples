package com.nytimes.pubp.articles.service.flow.impl.steps;

import com.nytimes.pubp.articles.domain.Article;
import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.PublishStep;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;

public class VerifyHeadline implements PublishStep {

    private PublishStep next;

    public VerifyHeadline() {
    }

    public VerifyHeadline(PublishStep next) {
        this.next = next;
    }

    @Override
    public void execute(PublishContext context) throws PublishException {
        Article article = context.getArticle();
        if (article.getHeadline() == null || article.getHeadline().isBlank()) {
            context.addError("Headline cannot be empty");
        }
        executeNext(context);
    }

    @Override
    public PublishStep getNext() {
        return next;
    }
}
