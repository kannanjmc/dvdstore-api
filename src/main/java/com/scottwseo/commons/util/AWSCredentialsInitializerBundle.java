package com.scottwseo.commons.util;

import com.scottwseo.commons.config.EnvVariables;
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
        // read it from home/.aws/credential file
        String profile = EnvVariables.AWS_PROFILE.value();

        System.setProperty("aws.accessKeyId", null); // EnvVariables.ACCESS_KEY_ID.value()
        System.setProperty("aws.secretKey", null); //EnvVariables.SECRET_KEY.value()
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

}
