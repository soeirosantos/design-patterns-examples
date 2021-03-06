package com.nytimes.pubp.articles.service.flow;

import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;

import java.util.Optional;

public interface PublishStep {

    void doExecute(PublishContext context) throws PublishException;

    PublishStep getNext();

    default void execute(PublishContext context) throws PublishException {
        if (Optional.ofNullable(getNext()).isPresent()) {
            getNext().execute(context);
        }
    }
}
