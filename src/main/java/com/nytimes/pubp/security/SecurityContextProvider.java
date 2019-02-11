package com.nytimes.pubp.security;

import com.nytimes.pubp.security.impl.JaxRsSecurityContextProvider;
import com.nytimes.pubp.security.impl.LocalSecurityContextProvider;

public interface SecurityContextProvider {

    static SecurityContextProvider provider(String environment) {
        if ("local".equals(environment)) {
            return new LocalSecurityContextProvider();
        } else {
            return new JaxRsSecurityContextProvider();
        }
    }

    SecurityContext get();
}
