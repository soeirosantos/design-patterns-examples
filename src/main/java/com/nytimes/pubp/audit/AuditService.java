package com.nytimes.pubp.audit;

import com.nytimes.pubp.audit.exception.AuditException;

public interface AuditService {
    void log(String uri, String user, String service) throws AuditException;
}
