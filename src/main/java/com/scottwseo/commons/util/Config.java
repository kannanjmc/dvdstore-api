package com.scottwseo.commons.util;

import com.netflix.config.ConfigurationManager;
import com.scottwseo.commons.util.cfg.ConfigType;
import com.scottwseo.commons.util.cfg.Configuration;

/**
 * Created by sseo on 9/6/16.
 */
public enum Config {

    @Configuration(type= ConfigType.TEXT)
    DB_URL("db.url"),

    @Configuration(type=ConfigType.TEXT)
    DB_USER("db.user"),

    @Configuration(type=ConfigType.TEXT)
    DB_PWD("db.pwd");

    private String key;

    Config(String key) {
        this.key = key;
    }

    public boolean isProvided() {
        String value = ConfigurationManager.getConfigInstance().getString(key);

        if (value == null || "".equals(value)) {
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

    public static boolean check() {
        boolean valid = true;

        for (Config config : Config.values()) {
            if (!config.isProvided()) {
                valid = false;
                break;
            }
        }

        return valid;
    }

}
