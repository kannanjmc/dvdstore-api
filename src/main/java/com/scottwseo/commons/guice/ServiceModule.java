package com.scottwseo.commons.guice;

import com.google.inject.AbstractModule;
import com.scottwseo.commons.app.APIConfiguration;
import io.dropwizard.setup.Environment;

public class ServiceModule extends AbstractModule {

    private APIConfiguration configuration;
    private Environment environment;

    public ServiceModule(APIConfiguration configuration, Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    @Override
    protected void configure() {
    }

}
