package com.scottwseo.commons.cfg;

import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import com.scottwseo.commons.util.S3Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class S3ConfigurationSource implements PolledConfigurationSource {

    private static Logger LOG = LoggerFactory.getLogger(S3ConfigurationSource.class);

    private String bucket;
    private String key;

    public S3ConfigurationSource(String bucket, String key) {
        this.bucket = bucket;
        this.key = key;
    }

    @Override
    public PollResult poll(boolean initial, Object checkPoint) throws Exception {
        Map<String, Object> map = load();
        return PollResult.createFull(map);
    }

    synchronized Map<String, Object> load() throws Exception {
        Map<String, Object> configs = new HashMap<String, Object>();

        try {
            InputStream s = S3Util.get(bucket, key);
            if (s != null) {
                Properties properties = new Properties();
                properties.load(s);

                for (String key : properties.stringPropertyNames()) {
                    String value = (String) properties.get(key);
                    configs.put(key, value);
                }
            }

            return configs;
        } catch(Exception e){
            LOG.error("s3.config.properties.get.failed");
        }

        return configs;
    }

}
