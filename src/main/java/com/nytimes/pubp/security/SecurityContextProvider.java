package com.nytimes.pubp.security;

import com.nytimes.pubp.config.impl.AppConfig;
import com.nytimes.pubp.security.impl.JaxRsSecurityContextProvider;
import com.nytimes.pubp.security.impl.LocalSecurityContextProvider;

public interface SecurityContextProvider {

    static SecurityContextProvider provider(AppConfig.Environment environment) {
        if (environment == AppConfig.Environment.LOCAL) {
            return new LocalSecurityContextProvider();
        } else {
            return new JaxRsSecurityContextProvider();
        }
    }

    SecurityContext get();
}
