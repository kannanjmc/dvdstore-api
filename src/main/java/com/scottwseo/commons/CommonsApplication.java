package com.scottwseo.commons;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.netflix.config.ConfigurationManager;
import com.scottwseo.commons.app.APIApplication;
import com.scottwseo.commons.app.APIConfiguration;
import com.scottwseo.commons.guice.ServiceModule;
import com.scottwseo.commons.health.ConfigHealthCheck;
import com.scottwseo.commons.health.DummyHealthCheck;
import com.scottwseo.commons.help.HelpView;
import com.scottwseo.commons.help.TailView;
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
        String applicationContextPath = applicationContextPath(configuration);

        if (EnvVariables.check() && Configs.check() && PostgreSQLDatabase.check()) {

            environment.healthChecks().register("config", new ConfigHealthCheck());

            environment.jersey().register(new HelpResource(new HelpView(applicationContextPath), new TailView(applicationContextPath), getName(), getAppVersion()));

            Injector injector = Guice.createInjector(new ServiceModule(configuration, environment));
        }
        else {
            environment.jersey().register(new StartupCheckListResource());
            environment.healthChecks().register("dummy", new DummyHealthCheck());
        }

        websocket.addEndpoint(LogEndPoint.class);
    }

    @Override
    public String getName() {
        return ConfigurationManager.getConfigInstance().getString(Configs.APP_NAME.key(), "API");
    }

    @Override
    protected String getAppVersion() {
        return getAppVersion("app.version", "v1.0.0");
    }

}
