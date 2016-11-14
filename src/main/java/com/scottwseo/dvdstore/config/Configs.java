package com.scottwseo.dvdstore.config;

import com.netflix.config.ConfigurationManager;
import com.scottwseo.commons.config.Config;
import com.scottwseo.commons.config.ConfigDataType;
import com.scottwseo.commons.config.ConfigManager;
import com.scottwseo.commons.config.Configuration;

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
            if (config.required() && !config.provided()) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    public boolean provided() {
        return ConfigManager.isProvided(this);
    }

    public String getString() {
        return value();
    }

    public String key() {
        return key;
    }

    public String value() {
        return ConfigurationManager.getConfigInstance().getString(key);
    }

    public boolean required() {
        return ConfigManager.isRequired(this);
    }

    public boolean masked() {
        return ConfigManager.isMasked(this);
    }

}
