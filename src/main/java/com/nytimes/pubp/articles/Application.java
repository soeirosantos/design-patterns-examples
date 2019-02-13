package com.nytimes.pubp.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nytimes.pubp.articles.api.resource.ArticleResource;
import com.nytimes.pubp.articles.dao.ArticleDao;
import com.nytimes.pubp.articles.service.PublishService;
import com.nytimes.pubp.articles.service.PublishServiceBuilder;
import com.nytimes.pubp.audit.AuditServiceFactory;
import com.nytimes.pubp.config.AppConfigLoader;
import com.nytimes.pubp.config.impl.AppConfig;
import com.nytimes.pubp.gateway.GatewayClientFactory;
import com.nytimes.pubp.notification.EmailNotificationServiceRegister;
import com.nytimes.pubp.notification.EmailNotificationServiceRegister.EmailNotificationType;
import com.nytimes.pubp.security.SecurityContextProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ext.ContextResolver;
import java.net.URI;

public class Application {

    public static void main(String[] args) throws Exception {
        final HttpServer server = createServer();
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        server.start();
        Thread.currentThread().join();
    }

    private static HttpServer createServer() {

        AppConfigLoader loader = AppConfigLoader.defaultLoader();
        AppConfig config = loader.load();

        EmailNotificationServiceRegister emailNotificationServiceRegister =
                new EmailNotificationServiceRegister(config);

        PublishService publishService = PublishServiceBuilder.builder()
                .withArticleDao(new ArticleDao())
                .withAuditService(AuditServiceFactory.create(config))
                .withSecurityContext(SecurityContextProvider.provider(config.getEnvironment()).get())
                .withGatewayClient(GatewayClientFactory.create(config))
                .withNotificationService(emailNotificationServiceRegister.lookup(EmailNotificationType.SEND_GRID))
                .build();

        final ResourceConfig rc = getResourceConfig(publishService);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(config.getBaseUrl()), rc, false);
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
