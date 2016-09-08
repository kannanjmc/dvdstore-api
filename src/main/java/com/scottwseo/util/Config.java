package com.scottwseo.util;

import com.scottwseo.util.cfg.ConfigType;
import com.scottwseo.util.cfg.Configuration;

/**
 * Created by sseo on 9/6/16.
 */
public enum Config {

    @Configuration(type= ConfigType.TEXT)
    DB_HOST("db.host"),

    @Configuration(type=ConfigType.TEXT)
    DB_USER("db.user"),

    @Configuration(type=ConfigType.TEXT)
    DB_PWD("db.pwd");

    private String key;

    Config(String key) {
        this.key = key;
    }

    public boolean isProvided() {
        if ("db.pwd".equals(key)) {
            return false;
        }
        return true;
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
