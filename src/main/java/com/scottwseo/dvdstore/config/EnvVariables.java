package com.scottwseo.dvdstore.config;

import com.scottwseo.commons.config.Config;
import com.scottwseo.commons.config.ConfigDataType;
import com.scottwseo.commons.config.ConfigManager;
import com.scottwseo.commons.config.Configuration;

/**
 * Created by seos on 9/8/16.
 */
public enum EnvVariables implements Config {

    @Configuration(type= ConfigDataType.TEXT, required = true, masked = false)
    CONFIG_URL("SWS_API_CONFIG_URL"),

    @Configuration(type= ConfigDataType.TEXT, required = false, masked = false)
    AWS_PROFILE("SWS_API_AWS_PROFILE");

    private String key;

    EnvVariables(String key) {
        this.key = key;
    }

    public static boolean check() {
        boolean valid = true;

        for (EnvVariables env : EnvVariables.values()) {
            if (env.required() && !env.provided()) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    public String key() {
        return key;
    }

    public String value() {
        return System.getenv(key);
    }

    public boolean required() {
        return ConfigManager.isRequired(this);
    }

    public boolean provided() {
        return ConfigManager.isProvided(this);
    }

    public boolean masked() {
        return ConfigManager.isMasked(this);
    }

}
