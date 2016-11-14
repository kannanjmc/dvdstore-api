package com.scottwseo.commons.config;

import com.netflix.config.*;
import com.scottwseo.commons.util.S3Util;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;

import static com.scottwseo.commons.util.LogUtil.warn;
import static com.scottwseo.commons.util.StringUtils.isEmpty;
import static com.scottwseo.commons.util.StringUtils.isNotEmpty;

/**
 * Created by seos on 9/8/16.
 */
public class ConfigManager {

    public static void initialize(String configUrl, String profileName) {
        try {
            InputStream is = null;
            String url = configUrl;

            if (isNotEmpty(profileName)) {
                String bucket = parseBucket(url);
                String key = parseKey(url);
                is = S3Util.get(bucket, key, profileName);
            }
            else {
                is = new URL(url).openStream();
            }

            PolledConfigurationSource s3ConfigurationSource = new InputStreamConfigurationSource(is);

            AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(0, 60 * 1000, true);

            DynamicConfiguration dynamicConfiguration = new DynamicConfiguration(s3ConfigurationSource, scheduler);

            ConfigurationManager.install(dynamicConfiguration);
        }
        catch (Exception e) {
            warn("config.initialization.failed", "", "url", configUrl, "profileName", profileName, "exception", e);
        }
    }

    public static boolean isRequired(Config config) {
        Configuration configuration = getAnnotation(config, Configuration.class);
        if (configuration != null) {
            return configuration.required();
        }
        return true;
    }

    public static boolean isProvided(Config config) {
        String value = config.value();

        if (isEmpty(value)) {
            return false;
        }
        return true;
    }

    public static boolean isMasked(Config config) {
        Configuration configuration = getAnnotation(config, Configuration.class);
        if (configuration != null) {
            return configuration.masked();
        }
        return true;
    }

    private static <A extends Annotation> A getAnnotation(Config config, Class<A> annotationType) {
        try {
            Class<? extends Config> configClass = config.getClass();
            A fieldAnnotation = configClass.getField(config.name()).getAnnotation(annotationType);
            A classAnnotation = configClass.getAnnotation(annotationType);

            return fieldAnnotation != null ? fieldAnnotation : classAnnotation;
        } catch (SecurityException e) {
            // ignore
        } catch (NoSuchFieldException e) {
            // ignore
        }
        return null;
    }

    private static String parseBucket(String url) {
        //url = "https://s3.amazonaws.com/config.scottwseo.com/dev/config.properties";
        url = url.replace("https://s3.amazonaws.com/", "");

        return url.substring(0, url.indexOf("/"));
    }

    private static String parseKey(String url) {
        //url = "https://s3.amazonaws.com/config.scottwseo.com/dev/config.properties";

        url = url.replace("https://s3.amazonaws.com/", "");

        // config.scottwseo.com/dev/config.properties

        return url.substring(url.indexOf("/") + 1, url.length());
    }

}
