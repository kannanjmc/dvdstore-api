package com.scottwseo.commons.util;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by seos on 9/8/16.
 */
public class AWSCredentialsInitializerBundle<T extends Configuration>  implements ConfiguredBundle<T> {

    public AWSCredentialsInitializerBundle() {
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        System.setProperty("aws.accessKeyId", System.getenv(EnvVariables.ACCESS_KEY_ID));
        System.setProperty("aws.secretKey", System.getenv(EnvVariables.SECRET_KEY));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

}
