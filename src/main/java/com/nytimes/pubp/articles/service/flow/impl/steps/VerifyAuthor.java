package com.nytimes.pubp.articles.service.flow.impl.steps;

import com.nytimes.pubp.articles.domain.Article;
import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.PublishStep;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;

public class VerifyAuthor implements PublishStep {

    private PublishStep next;

    public VerifyAuthor() {
    }

    public VerifyAuthor(PublishStep next) {
        this.next = next;
    }

    @Override
    public void executeNext(PublishContext context) throws PublishException {
        Article article = context.getArticle();
        if (article.getAuthor() == null || article.getAuthor().isBlank()) {
            context.addError("Author cannot be empty");
        }
    }

    @Override
    public PublishStep getNext() {
        return next;
    }
}
