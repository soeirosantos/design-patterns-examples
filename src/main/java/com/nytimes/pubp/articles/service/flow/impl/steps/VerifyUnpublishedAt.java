package com.nytimes.pubp.articles.service.flow.impl.steps;

import com.nytimes.pubp.articles.domain.Article;
import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.PublishStep;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;

import java.time.LocalDateTime;

public class VerifyUnpublishedAt implements PublishStep {

    private PublishStep next;

    public VerifyUnpublishedAt() {
    }

    public VerifyUnpublishedAt(PublishStep next) {
        this.next = next;
    }

    @Override
    public void doExecute(PublishContext context) throws PublishException {
        Article article = context.getArticle();
        if (article.getUnpublishedAt() == null || article.getUnpublishedAt().isAfter(LocalDateTime.now())) {
            context.addError("Unpublish date/time cannot be empty or a future date");
        }
    }

    @Override
    public PublishStep getNext() {
        return next;
    }
}
