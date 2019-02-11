package com.nytimes.pubp.security.impl;


import com.nytimes.pubp.security.SecurityContext;

public class LocalSecurityContext implements SecurityContext {

    public String getCurrentUser() {
        return "local-user";
    }
}
