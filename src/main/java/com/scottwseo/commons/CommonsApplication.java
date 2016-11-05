package com.scottwseo.commons;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scottwseo.commons.app.APIApplication;
import com.scottwseo.commons.app.APIConfiguration;
import com.scottwseo.commons.guice.ServiceModule;
import com.scottwseo.commons.health.ConfigHealthCheck;
import com.scottwseo.commons.health.DummyHealthCheck;
import com.scottwseo.commons.logging.LogEndPoint;
import com.scottwseo.commons.resources.HelpResource;
import com.scottwseo.commons.resources.StartupCheckListResource;
import com.scottwseo.commons.util.Configs;
import com.scottwseo.commons.util.EnvVariables;
import com.scottwseo.commons.util.PostgreSQLDatabase;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CommonsApplication extends APIApplication {

    public static void main(String[] args) throws Exception {
        java.security.Security.setProperty("networkaddress.cache.ttl", "60");
        if (args == null || args.length == 0) {
            args = new String[] {"server", "commons/conf/api.yml"};
        }
        new CommonsApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<APIConfiguration> bootstrap) {
        super.initialize(bootstrap);
    }

    @Override
    public void run(final APIConfiguration configuration,
                    final Environment environment) {

        if (EnvVariables.check() && Configs.check() && PostgreSQLDatabase.check()) {

            environment.healthChecks().register("config", new ConfigHealthCheck());

            Injector injector = Guice.createInjector(new ServiceModule(configuration, environment));

            environment.jersey().register(injector.getInstance(HelpResource.class));

        }
        else {
            environment.jersey().register(new StartupCheckListResource());
            environment.healthChecks().register("dummy", new DummyHealthCheck());
        }

        websocket.addEndpoint(LogEndPoint.class);
    }

}
