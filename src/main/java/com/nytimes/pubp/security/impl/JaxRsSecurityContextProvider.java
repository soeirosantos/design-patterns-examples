package com.nytimes.pubp.security.impl;

import com.nytimes.pubp.security.SecurityContext;
import com.nytimes.pubp.security.SecurityContextProvider;

public class JaxRsSecurityContextProvider implements SecurityContextProvider {

    @Override
    public SecurityContext get() {
        return new JaxRsSecurityContext();
    }
}
