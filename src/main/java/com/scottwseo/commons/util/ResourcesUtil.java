package com.scottwseo.commons.util;

import com.google.gson.*;
import com.scottwseo.dvdstore.api.Pagination;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by seos on 9/16/16.
 */
public class ResourcesUtil {

    private static final Logger LOG = LoggerFactory.getLogger("main");

    public static Pagination paginate(String uri, long startPage, long pageSize, long totalRecords) {
        if (startPage == 0) {
            startPage = 1;
        }

        boolean hasMore = (totalRecords > (startPage * pageSize));

        Long previousPage = (startPage > 1) ? (startPage - 1) : null;
        String prev = previousPage == null ? null : uri + "?" + "start=" + previousPage + "&size=" + pageSize;

        Long nextPage = (hasMore) ? startPage + 1 : null;
        String next = nextPage == null ? null : uri + "?" + "start=" + nextPage + "&size=" + pageSize;

        return new Pagination()
                .start(startPage)
                .next(next)
                .prev(prev)
                .total(totalRecords)
                .resultSize(pageSize)
                .totalPages(totalRecords / pageSize);
    }

    private static Map log(String message, String description, String level, Object... context) {
        Map map = new Hashtable();
        map.put("message", message);
        map.put("description", description);
        map.putAll(asMap(context));
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

    public static Map<String, Object> asMap(Object... objects) {

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

    public static String asJson(Object o) {
        return gson().toJson(o);
    }

    public static Gson gson() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .disableHtmlEscaping()
                .registerTypeAdapter(Date.class, new ResourcesUtil.DateSerizlier())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();
        return gson;
    }

    public static <T> T fromJson(String json, Class clazz) {
        return (T) gson().fromJson(json, clazz);
    }

    private static class DateSerizlier implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) throws JsonParseException {
            return format(jsonElement.getAsString());
        }
    }

    private static String[] dateFormats = new String[] {
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd HH:mm:ss",
            "MM/dd/yyyy",
            "MM/dd/yyyy HH:mm:ss",
            "MM/dd/yyyy HH:mm",
            "M/d/yyyy hh:mm:ss a",
            "yyyy-MM-dd HH:mm:ss.0",
            "yyyy-MM-dd",
            "EEE MMM d kk:mm:ss z yyyy"};

    public static Date format(String strDate) {
        if (strDate == null) {
            return null;
        }

        DateTime dateTime = null;
        for (String format : dateFormats) {
            DateTimeFormatter df = DateTimeFormat.forPattern(format);
            try {
                dateTime = df.parseDateTime(strDate);
                return dateTime.toDate();
            }
            catch(IllegalArgumentException ire) {
                // do nothing, try the next format
            }
        }

        return null;
    }

}
