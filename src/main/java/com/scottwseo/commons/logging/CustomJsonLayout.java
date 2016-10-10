package com.scottwseo.commons.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.JsonFormatter;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import ch.qos.logback.core.CoreConstants;
import com.scottwseo.commons.util.JsonUtil;

import java.util.Map;

/**
 * Created by seos on 10/9/16.
 */
public class CustomJsonLayout extends JsonLayout {

    @Override
    public String doLayout(ILoggingEvent event) {
        Map map = toJsonMap(event);
        if (map == null || map.isEmpty()) {
            return null;
        }
        String result = getStringFromFormatter(map);
        return isAppendLineSeparator() ? result + CoreConstants.LINE_SEPARATOR : result;
    }

    private String getStringFromFormatter(Map map) {
        JsonFormatter formatter = getJsonFormatter();
        if (formatter == null) {
            addError("JsonFormatter has not been configured on JsonLayout instance " + getClass().getName() + ".  Defaulting to map.toString().");
            return map.toString();
        }

        try {
            String message = (String) map.get("message");
            Map msgMap = JsonUtil.fromJson(message, Map.class);
            map.put("log", msgMap);
            map.remove("message");
            return formatter.toJsonString(map);
        } catch (Exception e) {
            addError("JsonFormatter failed.  Defaulting to map.toString().  Message: " + e.getMessage(), e);
            return map.toString();
        }
    }

}
