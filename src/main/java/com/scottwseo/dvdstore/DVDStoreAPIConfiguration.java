package com.scottwseo.dvdstore;

import com.scottwseo.commons.config.Config;
import com.scottwseo.commons.rest.app.APIConfiguration;
import com.scottwseo.dvdstore.config.Configs;

public class DVDStoreAPIConfiguration extends APIConfiguration {

    protected  String getConfig(String key) {
        for (Config config : Configs.values()) {
            if (key.equals(config.key())) {
                return config.value();
            }
        }

        return null;
    }

}
