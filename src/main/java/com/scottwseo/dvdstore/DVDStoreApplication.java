package com.scottwseo.dvdstore;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.netflix.config.ConfigurationManager;
import com.scottwseo.commons.app.APIApplication;
import com.scottwseo.commons.app.APIConfiguration;
import com.scottwseo.commons.health.DummyHealthCheck;
import com.scottwseo.commons.logging.LogEndPoint;
import com.scottwseo.commons.resources.HelpResource;
import com.scottwseo.commons.resources.StartupCheckListResource;
import com.scottwseo.commons.util.Configs;
import com.scottwseo.commons.util.EnvVariables;
import com.scottwseo.commons.util.PostgreSQLDatabase;
import com.scottwseo.dvdstore.guice.DVDStoreServiceModule;
import com.scottwseo.dvdstore.resources.CategoryResource;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DVDStoreApplication extends APIApplication {

    public static void main(String[] args) throws Exception {
        java.security.Security.setProperty("networkaddress.cache.ttl", "60");
        if (args == null || args.length == 0) {
            args = new String[] {"server", "dvdstore/conf/api.yml"};
        }
        new DVDStoreApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<APIConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new AssetsBundle("/dvdswagger", "/dvdswagger", "swagger.json", "dvdswagger"));
    }

    @Override
    public void run(final APIConfiguration configuration,
                    final Environment environment) {

        String applicationContextPath = applicationContextPath(configuration);

        if (EnvVariables.check() && Configs.check() && PostgreSQLDatabase.check()) {

            Injector injector = Guice.createInjector(new DVDStoreServiceModule(configuration, environment));

            environment.jersey().register(new HelpResource(applicationContextPath, getName(), getAppVersion()));

            CategoryResource categoryResource = injector.getInstance(CategoryResource.class);

            environment.jersey().register(categoryResource);
        }
        else {
            environment.jersey().register(new StartupCheckListResource());
            environment.healthChecks().register("dummy", new DummyHealthCheck());
        }

        websocket.addEndpoint(LogEndPoint.class);
    }

    @Override
    public String getName() {
        return ConfigurationManager.getConfigInstance().getString(Configs.APP_NAME.key(), "DVD Store");
    }

    @Override
    protected String getAppVersion() {
        return getAppVersion("app.version", "v1.0.0");
    }

}
