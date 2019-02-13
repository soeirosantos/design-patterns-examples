package com.nytimes.pubp.articles.service.flow.impl.steps;

import com.nytimes.pubp.articles.domain.Article;
import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.PublishStep;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;

import java.time.LocalDateTime;
import java.util.Optional;

public class VerifyPublishedAt implements PublishStep {

    private PublishStep next;

    public VerifyPublishedAt() {
    }

    public VerifyPublishedAt(PublishStep next) {
        this.next = next;
    }

    @Override
    public void execute(PublishContext context) throws PublishException {
        Article article = context.getArticle();
        if (article.getPublishedAt() == null || article.getPublishedAt().isAfter(LocalDateTime.now())) {
            context.addError("Publish date/time cannot be empty or a future date");
        }

        if (Optional.ofNullable(next).isPresent()) {
            next.execute(context);
        }
    }
}
