package com.nytimes.pubp.articles.service.flow.impl.steps;

import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.PublishStep;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;

import java.time.LocalDateTime;

public class SetModifiedAt implements PublishStep {

    private PublishStep next;

    public SetModifiedAt() {
    }

    public SetModifiedAt(PublishStep next) {
        this.next = next;
    }

    @Override
    public void executeNext(PublishContext context) throws PublishException {
        context.getArticle().setModifiedAt(LocalDateTime.now());
    }

    @Override
    public PublishStep getNext() {
        return next;
    }
}
