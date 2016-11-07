package com.scottwseo.commons.config;

import com.netflix.config.*;
import com.scottwseo.commons.util.S3Util;

import java.io.InputStream;
import java.net.URL;

import static com.scottwseo.commons.config.EnvVariables.AWS_PROFILE;
import static com.scottwseo.commons.config.EnvVariables.CONFIG_URL;
import static com.scottwseo.commons.util.LogUtil.warn;
import static com.scottwseo.commons.util.StringUtils.isNotEmpty;

/**
 * Created by seos on 9/8/16.
 */
public class ConfigInitializer {


    public static void initialize() {
        try {
            InputStream is = null;
            String url = CONFIG_URL.value();

            if (isNotEmpty(AWS_PROFILE.value())) {
                String bucket = parseBucket(url);
                String key = parseKey(url);
                is = S3Util.get(bucket, key);
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
            warn("config.initialization.failed", "", "exception", e);
        }
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
