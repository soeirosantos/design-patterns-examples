package com.nytimes.pubp.articles.service.flow;

import com.nytimes.pubp.articles.service.ServiceType;
import com.nytimes.pubp.articles.service.flow.PublishStep;
import com.nytimes.pubp.articles.service.flow.impl.steps.SetModifiedAt;
import com.nytimes.pubp.articles.service.flow.impl.steps.SetUri;
import com.nytimes.pubp.articles.service.flow.impl.steps.ValidateArticle;
import com.nytimes.pubp.articles.service.flow.impl.steps.VerifyAuthor;
import com.nytimes.pubp.articles.service.flow.impl.steps.VerifyHeadline;
import com.nytimes.pubp.articles.service.flow.impl.steps.VerifyPublishedAt;
import com.nytimes.pubp.articles.service.flow.impl.steps.VerifyUnpublishedAt;

import java.util.HashMap;
import java.util.Map;

public class PublishFlow {

    private static final Map<ServiceType, PublishStep> flow = new HashMap<>();

    static {
        flow.put(ServiceType.PUBLISH, setupPublishFlow());
        flow.put(ServiceType.UNPUBLISH, setupUnpublishFlow());
    }

    public static PublishStep getFlow(ServiceType serviceType) {
        return flow.get(serviceType);
    }

    private static PublishStep setupPublishFlow() {
        SetUri setUri = new SetUri();
        SetModifiedAt setModifiedAt = new SetModifiedAt(setUri);
        ValidateArticle validateArticle = new ValidateArticle(setModifiedAt);
        VerifyPublishedAt verifyPublishedAt = new VerifyPublishedAt(validateArticle);
        VerifyHeadline verifyHeadline = new VerifyHeadline(verifyPublishedAt);
        return new VerifyAuthor(verifyHeadline);
    }

    private static PublishStep setupUnpublishFlow() {
        SetModifiedAt setModifiedAt = new SetModifiedAt();
        ValidateArticle validateArticle = new ValidateArticle(setModifiedAt);
        VerifyUnpublishedAt verifyUnpublishedAt = new VerifyUnpublishedAt(validateArticle);
        VerifyHeadline verifyHeadline = new VerifyHeadline(verifyUnpublishedAt);
        return new VerifyAuthor(verifyHeadline);
    }
}
