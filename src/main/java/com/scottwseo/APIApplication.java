package com.scottwseo;

import com.scottwseo.health.ConfigHealthCheck;
import com.scottwseo.resources.ConfigurationResource;
import com.scottwseo.util.Config;
import com.scottwseo.util.PostgreSQLDatabase;
import com.scottwseo.util.cfg.ArchaiusS3ConfigSourceBundle;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class APIApplication extends Application<APIConfiguration> {

    public static void main(final String[] args) throws Exception {
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

        bootstrap.addBundle(new ViewBundle<APIConfiguration>());
    }

    @Override
    public void run(final APIConfiguration configuration,
                    final Environment environment) {

        boolean ok = Config.check() && PostgreSQLDatabase.check();

        if (ok) {
            environment.healthChecks().register("config.check", new ConfigHealthCheck());
        }
        else {
            environment.jersey().register(new ConfigurationResource());
        }

    }

}
