package com.nytimes.pubp.articles.service.flow.impl.steps;

import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.PublishStep;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;

import java.time.LocalDateTime;
import java.util.Optional;

public class SetModifiedAt implements PublishStep {

    private PublishStep next;

    public SetModifiedAt() {
    }

    public SetModifiedAt(PublishStep next) {
        this.next = next;
    }

    @Override
    public void execute(PublishContext context) throws PublishException {
        context.getArticle().setModifiedAt(LocalDateTime.now());

        if (Optional.ofNullable(next).isPresent()) {
            next.execute(context);
        }
    }
}
