package com.nytimes.pubp.articles.service.exception;

import java.util.List;

public class InvalidArticleException extends PublishException {

    private final List<String> errors;

    public InvalidArticleException(List<String> errors) {
        super(errors.toString());
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
