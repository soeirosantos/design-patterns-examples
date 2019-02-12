package com.nytimes.pubp.articles.service;

import com.nytimes.pubp.articles.dao.ArticleDao;
import com.nytimes.pubp.articles.dao.exception.DaoException;
import com.nytimes.pubp.articles.domain.Article;
import com.nytimes.pubp.articles.service.exception.InvalidArticleException;
import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.audit.AuditService;
import com.nytimes.pubp.audit.exception.AuditException;
import com.nytimes.pubp.gateway.impl.GatewayClient;
import com.nytimes.pubp.gateway.exception.GatewayClientException;
import com.nytimes.pubp.notification.EmailNotificationService;
import com.nytimes.pubp.notification.exception.NotificationException;
import com.nytimes.pubp.security.SecurityContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PublishService {

    private static final String NYT_ARTICLE_SCHEME_PATH = "nyt://article/";

    private final ArticleDao articleDao;
    private final AuditService auditService;
    private final SecurityContext securityContext;
    private final GatewayClient gatewayClient;
    private final EmailNotificationService notificationService;

    PublishService(ArticleDao articleDao, AuditService auditService, SecurityContext securityContext,
                          GatewayClient gatewayClient, EmailNotificationService notificationService) {
        this.articleDao = articleDao;
        this.auditService = auditService;
        this.securityContext = securityContext;
        this.gatewayClient = gatewayClient;
        this.notificationService = notificationService;
    }

    public void publish(Article article) throws PublishException {

        List<String> errors = new ArrayList<>();

        if (article.getAuthor() == null || article.getAuthor().isBlank()) {
            errors.add("Author cannot be empty");
        }

        if (article.getHeadline() == null || article.getHeadline().isBlank()) {
            errors.add("Headline cannot be empty");
        }

        if (article.getPublishedAt() == null || article.getPublishedAt().isAfter(LocalDateTime.now())) {
            errors.add("Publish date/time cannot be empty or a future date");
        }

        if (!errors.isEmpty()) {
            throw new InvalidArticleException(errors);
        }

        article.setModifiedAt(LocalDateTime.now());
        article.setUri(NYT_ARTICLE_SCHEME_PATH + UUID.randomUUID().toString());

        try {

            articleDao.save(article);
            gatewayClient.publish(article);
            auditService.log(article.getUri(), securityContext.getCurrentUser(), "PUBLISH");
            notificationService.notify(article.getUri(), "PUBLISH");

        } catch (DaoException | GatewayClientException | AuditException | NotificationException e) {

            throw new PublishException("Error publishing article", e);

        }
    }

    public void unpublish(Article article) throws PublishException {
        List<String> errors = new ArrayList<>();

        if (article.getAuthor() == null || article.getAuthor().isBlank()) {
            errors.add("Author cannot be empty");
        }

        if (article.getHeadline() == null || article.getHeadline().isBlank()) {
            errors.add("Headline cannot be empty");
        }

        if (article.getUnpublishedAt() == null || article.getUnpublishedAt().isAfter(LocalDateTime.now())) {
            errors.add("Unpublish date/time cannot be empty or a future date");
        }

        if (article.getUri() == null || article.getUri().isBlank()) {
            errors.add("URI cannot be empty");
        }

        if (!errors.isEmpty()) {
            throw new InvalidArticleException(errors);
        }

        article.setModifiedAt(LocalDateTime.now());

        try {

            articleDao.save(article);
            gatewayClient.unpublish(article);
            auditService.log(article.getUri(), securityContext.getCurrentUser(), "UNPUBLISH");
            notificationService.notify(article.getUri(), "UNPUBLISH");

        } catch (DaoException | GatewayClientException | AuditException | NotificationException e) {

            throw new PublishException("Error unpublishing article", e);

        }
    }

    public void preview(Article article) throws PublishException {
        //TODO to be implemented
    }
}
