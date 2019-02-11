package com.nytimes.pubp.security.impl;


import com.nytimes.pubp.security.SecurityContext;

public class JaxRsSecurityContext implements SecurityContext {

    public String getCurrentUser() {
        //TODO - do some stuff to get the Principal from the JAX-RS context
        return "fake-principal";
    }
}
