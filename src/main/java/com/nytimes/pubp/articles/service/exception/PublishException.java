package com.nytimes.pubp.articles.service.exception;

public class PublishException extends Exception {

    PublishException(String message) {
        super(message);
    }

    public PublishException(String message, Throwable cause) {
        super(message, cause);
    }
}
