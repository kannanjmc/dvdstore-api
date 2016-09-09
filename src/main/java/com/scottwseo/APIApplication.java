package com.scottwseo;

import com.scottwseo.auth.SimpleAuthenticator;
import com.scottwseo.auth.SimpleAuthorizer;
import com.scottwseo.auth.User;
import com.scottwseo.health.ConfigHealthCheck;
import com.scottwseo.help.HelpResource;
import com.scottwseo.help.HelpView;
import com.scottwseo.resources.ConfigurationResource;
import com.scottwseo.util.Config;
import com.scottwseo.util.Constants;
import com.scottwseo.util.PostgreSQLDatabase;
import com.scottwseo.util.cfg.ArchaiusS3ConfigSourceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

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

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/help", "/com/scottwseo/help", "index.html", "help"));

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/swagger", "/com/scottwseo/swagger", "index.html", "swagger"));

    }

    @Override
    public void run(final APIConfiguration configuration,
                    final Environment environment) {

        System.setProperty("aws.accessKeyId", System.getenv(Constants.ACCESS_KEY_ID));
        System.setProperty("aws.secretKey", System.getenv(Constants.SECRET_KEY));

        boolean ok = Config.check() && PostgreSQLDatabase.check();

        if (ok) {
            environment.healthChecks().register("config.check", new ConfigHealthCheck());
            environment.jersey().register(new AuthDynamicFeature(
                    new BasicCredentialAuthFilter.Builder<User>()
                            .setAuthenticator(new SimpleAuthenticator())
                            .setAuthorizer(new SimpleAuthorizer())
                            .setRealm("API")
                            .buildAuthFilter()));
            environment.jersey().register(RolesAllowedDynamicFeature.class);
            //If you want to use @Auth to inject a custom Principal type into your resource
            environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

            environment.jersey().register(new HelpResource(new HelpView()));
        }
        else {
            environment.jersey().register(new ConfigurationResource());
        }

    }

}
