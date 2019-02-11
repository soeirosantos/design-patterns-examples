package com.nytimes.pubp.articles.domain;

import java.time.LocalDateTime;

public class Article {

    private String uri;
    private String headline;
    private String body;
    private String author;
    private String slug;
    private LocalDateTime publishedAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime unpublishedAt;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHeadline() {
        return headline;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public LocalDateTime getUnpublishedAt() {
        return unpublishedAt;
    }

    public void setUnpublishedAt(LocalDateTime unpublishedAt) {
        this.unpublishedAt = unpublishedAt;
    }

    @Override
    public String toString() {
        return "Article{" +
                "headline='" + headline + '\'' +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
