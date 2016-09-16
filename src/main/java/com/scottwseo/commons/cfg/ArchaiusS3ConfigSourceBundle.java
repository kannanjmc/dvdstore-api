package com.scottwseo.commons.cfg;

import com.netflix.config.*;
import com.scottwseo.commons.util.EnvVariables;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by seos on 9/8/16.
 */
public class ArchaiusS3ConfigSourceBundle <T extends Configuration>  implements ConfiguredBundle<T> {

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        String bucket = EnvVariables.S3_BUCKET.getString();

        String key = EnvVariables.S3_KEY.getString();

        PolledConfigurationSource s3ConfigurationSource = new S3ConfigurationSource(bucket, key);

        AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(0, 60 * 1000, true);

        DynamicConfiguration dynamicConfiguration = new DynamicConfiguration(s3ConfigurationSource, scheduler);

        ConfigurationManager.install(dynamicConfiguration);
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

}
