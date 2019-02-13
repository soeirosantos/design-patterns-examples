package com.nytimes.pubp.articles.service;

import com.nytimes.pubp.articles.dao.ArticleDao;
import com.nytimes.pubp.articles.dao.exception.DaoException;
import com.nytimes.pubp.articles.domain.Article;
import com.nytimes.pubp.articles.service.exception.PublishException;
import com.nytimes.pubp.articles.service.flow.PublishFlow;
import com.nytimes.pubp.articles.service.flow.PublishStep;
import com.nytimes.pubp.articles.service.flow.impl.PublishContext;
import com.nytimes.pubp.audit.AuditService;
import com.nytimes.pubp.audit.exception.AuditException;
import com.nytimes.pubp.gateway.GatewayClientExecutor;
import com.nytimes.pubp.gateway.exception.GatewayClientException;
import com.nytimes.pubp.gateway.impl.GatewayClient;
import com.nytimes.pubp.notification.EmailNotificationService;
import com.nytimes.pubp.notification.exception.NotificationException;
import com.nytimes.pubp.security.SecurityContext;

public class PublishService {

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

        PublishStep flow = PublishFlow.getFlow(ServiceType.PUBLISH);
        PublishContext context = PublishContext.create(article);
        flow.execute(context);

        publish(article, ServiceType.PUBLISH, gatewayClient::publish);
    }

    public void unpublish(Article article) throws PublishException {

        PublishStep flow = PublishFlow.getFlow(ServiceType.UNPUBLISH);
        PublishContext context = PublishContext.create(article);
        flow.execute(context);

        publish(article, ServiceType.UNPUBLISH, gatewayClient::unpublish);
    }

    public void preview(Article article) throws PublishException {
        //TODO to be implemented
    }

    private void publish(Article article, ServiceType serviceType, GatewayClientExecutor gatewayClientExecutor)
            throws PublishException {
        try {

            articleDao.save(article);
            gatewayClientExecutor.execute(article);
            auditService.log(article.getUri(), securityContext.getCurrentUser(), serviceType.name());
            notificationService.notify(article.getUri(), serviceType.name());

        } catch (DaoException | GatewayClientException | AuditException | NotificationException e) {

            throw new PublishException("Error executing " + serviceType.name() + " for article", e);

        }
    }
}



