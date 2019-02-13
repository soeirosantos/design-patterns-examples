package com.nytimes.pubp.articles.service.flow.impl;

import com.nytimes.pubp.articles.domain.Article;

import java.util.ArrayList;
import java.util.List;

public class PublishContext {

    private final Article article;
    private final List<String> errors = new ArrayList<>();

    public PublishContext(Article article) {
        this.article = article;
    }

    public static PublishContext create(Article article) {
        return new PublishContext(article);
    }

    public Article getArticle() {
        return article;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
