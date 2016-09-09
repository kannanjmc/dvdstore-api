package com.scottwseo.commons.util.cfg;

/**
 * Created by sseo on 9/6/16.
 */
public class ConfigManager {

    private static ConfigManager ourInstance = new ConfigManager();

    public static ConfigManager getInstance() {
        return ourInstance;
    }

    private ConfigManager() {
    }

    public Object getValue(String name) {
        return null;
    }

}
