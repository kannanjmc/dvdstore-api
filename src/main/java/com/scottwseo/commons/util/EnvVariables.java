package com.scottwseo.commons.util;

import com.scottwseo.commons.cfg.Config;
import com.scottwseo.commons.cfg.ConfigDataType;
import com.scottwseo.commons.cfg.Configuration;

import static com.scottwseo.commons.util.ConfigUtil.isRequired;
import static com.scottwseo.commons.util.StringUtils.isEmpty;

/**
 * Created by seos on 9/8/16.
 */
public enum EnvVariables implements Config {

    @Configuration(type= ConfigDataType.TEXT, required = true, masked = false)
    CONFIG_URL("com.scottwseo.api.CONFIG_URL"),

    @Configuration(type= ConfigDataType.TEXT, required = false, masked = false)
    AWS_PROFILE("com.scottwseo.api.AWS_PROFILE");

    private String key;

    EnvVariables(String key) {
        this.key = key;
    }

    public boolean isProvided() {
        String value = System.getenv(key);

        if (isEmpty(value)) {
            return false;
        }
        return true;
    }

    public String value() {
        return System.getenv(key);
    }

    public String key() {
        return key;
    }

    public static boolean check() {
        boolean valid = true;

        for (EnvVariables env : EnvVariables.values()) {
            if (isRequired(env) && !env.isProvided()) {
                valid = false;
                break;
            }
        }

        return valid;
    }

}
