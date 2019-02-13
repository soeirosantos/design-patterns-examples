package com.nytimes.pubp.articles.api.resource;


import com.nytimes.pubp.articles.api.error.ApiError;
import com.nytimes.pubp.articles.domain.Article;
import com.nytimes.pubp.articles.service.PublishService;
import com.nytimes.pubp.articles.service.ServiceType;
import com.nytimes.pubp.articles.service.exception.InvalidArticleException;
import com.nytimes.pubp.articles.service.exception.PublishException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleResource.class);

    private final PublishService publishService;

    public ArticleResource(PublishService publishService) {
        this.publishService = publishService;
    }

    @POST
    public Response publish(Article article) {
        try {

            publishService.publish(article, ServiceType.PUBLISH);
            return Response.ok(article).build();
        } catch (InvalidArticleException e) {

            ApiError error = new ApiError(e.getErrors());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        } catch (PublishException e) {

            LOG.error("Unexpected error publishing ", e);
            return Response.serverError().build();
        }
    }

    @DELETE
    public Response unpublish(Article article) {
        try {

            publishService.publish(article, ServiceType.UNPUBLISH);
            return Response.ok().build();
        } catch (InvalidArticleException e) {

            ApiError error = new ApiError(e.getErrors());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        } catch (PublishException e) {

            LOG.error("Unexpected error publishing ", e);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/preview")
    public Response preview(Article article) {
        try {

            publishService.publish(article, ServiceType.PREVIEW);
            return Response.ok(article).build();
        } catch (InvalidArticleException e) {

            ApiError error = new ApiError(e.getErrors());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        } catch (PublishException e) {

            LOG.error("Unexpected error publishing ", e);
            return Response.serverError().build();
        }
    }
}
