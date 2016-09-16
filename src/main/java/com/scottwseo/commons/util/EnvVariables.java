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

    @Configuration(type= ConfigDataType.TEXT)
    S3_BUCKET("com.scottwseo.api.S3_BUCKET"),

    @Configuration(type= ConfigDataType.TEXT)
    S3_KEY("com.scottwseo.api.S3_KEY"),

    @Configuration(type= ConfigDataType.TEXT, required = false, masked = true)
    ACCESS_KEY_ID("com.scottwseo.api.ACCESS_KEY_ID"),

    @Configuration(type= ConfigDataType.TEXT, required = false, masked = true)
    SECRET_KEY("com.scottwseo.api.SECRET_KEY");

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

    public String getString() {
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
