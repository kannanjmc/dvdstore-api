package com.scottwseo.commons.util;

import com.netflix.config.ConfigurationManager;
import com.scottwseo.commons.cfg.Config;
import com.scottwseo.commons.cfg.ConfigDataType;
import com.scottwseo.commons.cfg.Configuration;

import static com.scottwseo.commons.util.ConfigUtil.isRequired;
import static com.scottwseo.commons.util.StringUtils.isEmpty;

/**
 * Created by sseo on 9/6/16.
 */
public enum Configs implements Config {

    @Configuration(type = ConfigDataType.TEXT)
    DB_URL("db.url"),

    @Configuration(type = ConfigDataType.TEXT)
    DB_USER("db.user"),

    @Configuration(type = ConfigDataType.TEXT, masked = true)
    DB_PWD("db.pwd"),

    @Configuration(type = ConfigDataType.TEXT, required = false)
    GRAPHITE_HOST("graphite.host"),

    @Configuration(type = ConfigDataType.TEXT, required = false)
    APP_NAME("appname");

    private String key;

    Configs(String key) {
        this.key = key;
    }

    public static boolean check() {
        boolean valid = true;

        for (Configs config : Configs.values()) {
            if (isRequired(config) && !config.isProvided()) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    public boolean isProvided() {
        String value = ConfigurationManager.getConfigInstance().getString(key);

        if (isEmpty(value)) {
            return false;
        }
        return true;
    }

    public String getString() {
        return ConfigurationManager.getConfigInstance().getString(key);
    }

    public String key() {
        return key;
    }

}
