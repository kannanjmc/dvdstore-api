package com.scottwseo.dvdstore;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scottwseo.commons.logging.LogEndPoint;
import com.scottwseo.commons.rest.app.APIApplication;
import com.scottwseo.commons.rest.app.APIConfiguration;
import com.scottwseo.dvdstore.guice.DVDStoreServiceModule;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DVDStoreApplication extends APIApplication {

    private Injector injector;

    @Override
    public void initialize(final Bootstrap<APIConfiguration> bootstrap) {
        super.initialize(bootstrap);
    }

    @Override
    public void run(final APIConfiguration configuration,
                    final Environment environment) {

        java.security.Security.setProperty("networkaddress.cache.ttl", "60");

        Injector injector = Guice.createInjector(new DVDStoreServiceModule(configuration, environment));

        LaunchHandlerProvider launchHandlerProvider = injector.getInstance(LaunchHandlerProvider.class);

        LaunchHandler launchHandler = launchHandlerProvider.get();

        launchHandler.run(environment);

        websocket.addEndpoint(LogEndPoint.class);

    }


}
