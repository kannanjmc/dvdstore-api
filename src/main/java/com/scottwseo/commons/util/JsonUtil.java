package com.scottwseo.commons.util;

import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by seos on 9/16/16.
 */
public class JsonUtil {

    public static String asJson(Object o) {
        return gson().toJson(o);
    }

    public static Gson gson() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .disableHtmlEscaping()
                .registerTypeAdapter(Date.class, new JsonUtil.DateSerizlier())
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
