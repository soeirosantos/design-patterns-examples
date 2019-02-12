package com.nytimes.pubp.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nytimes.pubp.articles.api.resource.ArticleResource;
import com.nytimes.pubp.articles.dao.ArticleDao;
import com.nytimes.pubp.articles.service.PublishService;
import com.nytimes.pubp.articles.service.PublishServiceBuilder;
import com.nytimes.pubp.audit.AuditService;
import com.nytimes.pubp.audit.AuditServiceFactory;
import com.nytimes.pubp.gateway.GatewayClientFactory;
import com.nytimes.pubp.gateway.impl.GatewayClient;
import com.nytimes.pubp.notification.EmailNotificationService;
import com.nytimes.pubp.notification.EmailNotificationServiceRegister;
import com.nytimes.pubp.notification.EmailNotificationServiceRegister.EmailNotificationType;
import com.nytimes.pubp.security.SecurityContext;
import com.nytimes.pubp.security.SecurityContextProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ext.ContextResolver;
import java.net.URI;

public class Application {

    private static final String BASE_URL = "http://localhost:8080/publisher";

    public static void main(String[] args) throws Exception {
        final HttpServer server = createServer();
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        server.start();
        Thread.currentThread().join();
    }

    private static HttpServer createServer() {

        String environment = System.getenv("environment");

        ArticleDao articleDao = new ArticleDao();
        AuditService auditService = AuditServiceFactory.create();
        GatewayClient gatewayClient = GatewayClientFactory.create();
        SecurityContext securityContext = SecurityContextProvider.provider(environment).get();

        EmailNotificationServiceRegister emailNotificationServiceRegister = new EmailNotificationServiceRegister();
        EmailNotificationService notificationService = emailNotificationServiceRegister
                .lookup(EmailNotificationType.SEND_GRID);

        PublishService publishService = PublishServiceBuilder.builder()
                .withArticleDao(articleDao)
                .withAuditService(auditService)
                .withSecurityContext(securityContext)
                .withGatewayClient(gatewayClient)
                .withNotificationService(notificationService)
                .build();

        final ResourceConfig rc = getResourceConfig(publishService);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URL), rc, false);
    }

    private static ResourceConfig getResourceConfig(PublishService publishService) {
        return new ResourceConfig()
                .register(new ArticleResource(publishService))
                .register(LocalDateTimeMapper.class)
                .register(JacksonFeature.class);
    }

    static class LocalDateTimeMapper implements ContextResolver<ObjectMapper> {
        @Override
        public ObjectMapper getContext(Class<?> aClass) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            return mapper;
        }
    }
}
