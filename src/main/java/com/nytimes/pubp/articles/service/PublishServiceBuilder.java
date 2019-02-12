package com.nytimes.pubp.articles.service;

import com.nytimes.pubp.articles.dao.ArticleDao;
import com.nytimes.pubp.audit.AuditService;
import com.nytimes.pubp.gateway.impl.GatewayClient;
import com.nytimes.pubp.notification.EmailNotificationService;
import com.nytimes.pubp.security.SecurityContext;

public class PublishServiceBuilder {
    private ArticleDao articleDao;
    private AuditService auditService;
    private SecurityContext securityContext;
    private GatewayClient gatewayClient;
    private EmailNotificationService notificationService;

    public static PublishServiceBuilder builder() {
        return new PublishServiceBuilder();
    }

    public PublishServiceBuilder withArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
        return this;
    }

    public PublishServiceBuilder withAuditService(AuditService auditService) {
        this.auditService = auditService;
        return this;
    }

    public PublishServiceBuilder withSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
        return this;
    }

    public PublishServiceBuilder withGatewayClient(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
        return this;
    }

    public PublishServiceBuilder withNotificationService(EmailNotificationService notificationService) {
        this.notificationService = notificationService;
        return this;
    }

    public PublishService build() {
        return new PublishService(articleDao, auditService, securityContext, gatewayClient, notificationService);
    }
}