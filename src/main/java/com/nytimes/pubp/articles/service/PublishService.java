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

import java.util.HashMap;
import java.util.Map;

public class PublishService {

    private final ArticleDao articleDao;
    private final AuditService auditService;
    private final SecurityContext securityContext;
    private final Map<ServiceType, GatewayClientExecutor> gatewayClient = new HashMap<>();
    private final EmailNotificationService notificationService;

    PublishService(ArticleDao articleDao, AuditService auditService, SecurityContext securityContext,
                   GatewayClient gatewayClient, EmailNotificationService notificationService) {
        this.articleDao = articleDao;
        this.auditService = auditService;
        this.securityContext = securityContext;
        this.notificationService = notificationService;

        this.gatewayClient.put(ServiceType.PUBLISH, gatewayClient::publish);
        this.gatewayClient.put(ServiceType.UNPUBLISH, gatewayClient::unpublish);
    }

    public void publish(Article article, ServiceType serviceType) throws PublishException {

        PublishStep flow = PublishFlow.getFlow(serviceType);
        PublishContext context = PublishContext.create(article);
        flow.execute(context);

        publish(article, serviceType, gatewayClient.get(serviceType));
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



