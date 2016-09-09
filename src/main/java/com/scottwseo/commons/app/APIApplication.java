package com.scottwseo.commons.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scottwseo.commons.auth.AuthenticationBundle;
import com.scottwseo.commons.guice.ServiceModule;
import com.scottwseo.commons.health.ConfigHealthCheck;
import com.scottwseo.commons.help.HelpResource;
import com.scottwseo.commons.help.HelpView;
import com.scottwseo.commons.resources.ConfigurationResource;
import com.scottwseo.commons.togglz.TogglzBundle;
import com.scottwseo.commons.util.Config;
import com.scottwseo.commons.util.Constants;
import com.scottwseo.commons.util.PostgreSQLDatabase;
import com.scottwseo.commons.util.cfg.ArchaiusS3ConfigSourceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class APIApplication extends Application<APIConfiguration> {

    public static void main(final String[] args) throws Exception {
        java.security.Security.setProperty("networkaddress.cache.ttl", "60");
        launch(args);
    }

    public static void launch(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            args = new String[] {"server", "api/conf/api.yml"};
        }
        new APIApplication().run(args);
    }

    @Override
    public String getName() {
        return "API";
    }

    @Override
    public void initialize(final Bootstrap<APIConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        // Initialize config
        bootstrap.addBundle(new ArchaiusS3ConfigSourceBundle<APIConfiguration>());

        bootstrap.addBundle(new AuthenticationBundle<APIConfiguration>("Commons"));

        bootstrap.addBundle(new ViewBundle<APIConfiguration>());

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/commons/help", "/com/scottwseo/commons/help", "index.html", "help"));

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/commons/swagger", "/com/scottwseo/commons/swagger", "index.html", "swagger"));

        bootstrap.addBundle(new TogglzBundle<APIConfiguration>());
    }

    @Override
    public void run(final APIConfiguration configuration,
                    final Environment environment) {

        System.setProperty("aws.accessKeyId", System.getenv(Constants.ACCESS_KEY_ID));
        System.setProperty("aws.secretKey", System.getenv(Constants.SECRET_KEY));

        boolean ok = Config.check() && PostgreSQLDatabase.check();

        if (ok) {
            environment.healthChecks().register("config.check", new ConfigHealthCheck());

            environment.jersey().register(new HelpResource(new HelpView()));

            Injector injector = Guice.createInjector(new ServiceModule(configuration, environment));

        }
        else {
            environment.jersey().register(new ConfigurationResource());
        }

    }

}
