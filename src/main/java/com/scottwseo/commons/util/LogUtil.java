package com.scottwseo.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.scottwseo.commons.util.JsonUtil.asJson;

/**
 * Created by seos on 9/16/16.
 */
public class LogUtil {

    private static final Logger LOG = LoggerFactory.getLogger("");

    private static Map log(String message, String description, String level, Object... context) {
        Map map = new Hashtable();
        map.put("event", message);
        // don't print out empty description in the json
        if (description != null && description.length() > 0) {
            map.put("description", description);
        }
        map.putAll(map(context));
        switch (level) {
            case "debug": LOG.debug(asJson(map)); break;
            case "info": LOG.info(asJson(map)); break;
            case "warn": LOG.warn(asJson(map)); break;
            case "error": LOG.error(asJson(map)); break;
        }
        return map;
    }

    public static Map debug(String message, String description, Object... context) {
        return log(message, description, "debug", context);
    }

    public static Map info(String message, String description, Object... context) {
        return log(message, description, "info", context);
    }

    public static Map warn(String message, String description, Object... context) {
        return log(message, description, "warn", context);
    }

    public static Map error(String message, String description, Object... context) {
        return log(message, description, "error", context);
    }

    public static Map<String, Object> map(Object... objects) {

        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "The array has to be of an even size - size is "
                            + objects.length);
        }

        Map<String, Object> values = new HashMap<>();

        for (int x = 0; x < objects.length; x+=2) {
            values.put((String) objects[x], objects[x + 1]);
        }

        return values;

    }
}
