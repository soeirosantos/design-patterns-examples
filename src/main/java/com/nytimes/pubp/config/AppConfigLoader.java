package com.nytimes.pubp.config;

import com.nytimes.pubp.config.impl.AppConfig;
import com.nytimes.pubp.config.impl.DefaultConfigLoader;

public interface AppConfigLoader {

    AppConfig load();

    static AppConfigLoader defaultLoader() {
        return new DefaultConfigLoader();
    }
}
