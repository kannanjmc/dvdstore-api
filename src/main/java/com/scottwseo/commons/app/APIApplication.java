package com.scottwseo.commons.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scottwseo.commons.auth.AuthenticationBundle;
import com.scottwseo.commons.guice.ServiceModule;
import com.scottwseo.commons.health.ConfigHealthCheck;
import com.scottwseo.commons.health.DummyHealthCheck;
import com.scottwseo.commons.help.HelpView;
import com.scottwseo.commons.resources.HelpResource;
import com.scottwseo.commons.resources.StartupCheckListResource;
import com.scottwseo.commons.togglz.TogglzBundle;
import com.scottwseo.commons.util.AWSCredentialsInitializerBundle;
import com.scottwseo.commons.util.Config;
import com.scottwseo.commons.util.EnvVariables;
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

        if (EnvVariables.check()) {
            // AWSCredentialInitializerBundle is for allowing developers to manually specify the aws
            // credentials using environment variables
            bootstrap.addBundle(new AWSCredentialsInitializerBundle<APIConfiguration>());
            bootstrap.addBundle(new ArchaiusS3ConfigSourceBundle<APIConfiguration>());
        }

        bootstrap.addBundle(new TogglzBundle<APIConfiguration>());

        bootstrap.addBundle(new AuthenticationBundle<APIConfiguration>("Commons"));

        bootstrap.addBundle(new ViewBundle<APIConfiguration>());

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/commons/help", "/com/scottwseo/commons/help", "index.html", "help"));

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/commons/swagger", "/com/scottwseo/commons/swagger", "index.html", "swagger"));

    }

    @Override
    public void run(final APIConfiguration configuration,
                    final Environment environment) {

        if (EnvVariables.check() && Config.check() && PostgreSQLDatabase.check()) {

            environment.healthChecks().register("config", new ConfigHealthCheck());

            environment.jersey().register(new HelpResource(new HelpView()));

            Injector injector = Guice.createInjector(new ServiceModule(configuration, environment));

        }
        else {
            environment.jersey().register(new StartupCheckListResource());
            environment.healthChecks().register("dummy", new DummyHealthCheck());
        }

    }

}
