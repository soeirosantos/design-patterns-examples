package com.nytimes.pubp.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.nytimes.pubp.articles.api.resource.ArticleResource;
import com.nytimes.pubp.articles.dao.ArticleDao;
import com.nytimes.pubp.articles.service.PublishService;
import com.nytimes.pubp.audit.AuditService;
import com.nytimes.pubp.gateway.GatewayClient;
import com.nytimes.pubp.notification.EmailNotificationService;
import com.nytimes.pubp.security.SecurityContext;
import com.nytimes.pubp.security.impl.JaxRsSecurityContext;
import com.nytimes.pubp.security.impl.LocalSecurityContext;
import com.sendgrid.SendGrid;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ext.ContextResolver;
import java.net.URI;
import java.util.Optional;

public class Application {

    private static final String BASE_URL = "http://localhost:8080/publisher";

    public static void main(String[] args) throws Exception {
        final HttpServer server = createServer();
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        server.start();
        Thread.currentThread().join();
    }

    private static HttpServer createServer() {

        ArticleDao articleDao = new ArticleDao();

        Datastore datastore;
        if (Optional.ofNullable(System.getenv("DATASTORE_PROJECT_ID")).isPresent()) {
            DatastoreOptions.Builder datastoreBuilder = DatastoreOptions
                    .newBuilder()
                    .setProjectId(System.getenv("DATASTORE_PROJECT_ID"))
                    .setCredentials(DatastoreOptions.getDefaultInstance().getCredentials())
                    .setRetrySettings(DatastoreOptions
                            .getDefaultRetrySettings()
                            .toBuilder()
                            .setMaxAttempts(3).build())
                    .setTransportOptions(DatastoreOptions
                            .getDefaultHttpTransportOptions()
                            .toBuilder()
                            .setReadTimeout(1000)
                            .setConnectTimeout(1000).build());
            datastore = datastoreBuilder.build().getService();
        } else {
            datastore = DatastoreOptions.getDefaultInstance().getService();
        }

        AuditService auditService = new AuditService(datastore);

        SecurityContext securityContext;

        if ("local".equals(System.getenv("environment"))) {
            securityContext = new LocalSecurityContext();
        } else {
            securityContext = new JaxRsSecurityContext();
        }

        String gatewayHost = System.getenv("GATEWAY_HOST");
        Integer gatewayPort = Optional.ofNullable(System.getenv("GATEWAY_PORT"))
                .map(Integer::parseInt).orElseThrow();
        String gatewayRootCaPath = System.getenv("ROOT_CA_PATH");
        String gatewayCredentialsPath = System.getenv("CREDENTIALS_PATH");

        GatewayClient gatewayClient = new GatewayClient(gatewayHost, gatewayPort, gatewayRootCaPath,
                gatewayCredentialsPath);

        SendGrid sendGrid = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        EmailNotificationService notificationService = new EmailNotificationService(sendGrid);

        PublishService publishService =
                new PublishService(articleDao, auditService, securityContext, gatewayClient, notificationService);

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
