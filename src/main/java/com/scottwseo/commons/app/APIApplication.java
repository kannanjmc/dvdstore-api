package com.scottwseo.commons.app;

import com.scottwseo.commons.auth.AuthenticationBundle;
import com.scottwseo.commons.cfg.ConfigInitializer;
import com.scottwseo.commons.logging.WebsocketBundle;
import com.scottwseo.commons.togglz.TogglzBundle;
import com.scottwseo.commons.util.EnvVariables;
import com.smoketurner.dropwizard.zipkin.ZipkinBundle;
import com.smoketurner.dropwizard.zipkin.ZipkinFactory;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.views.ViewBundle;

public abstract class APIApplication extends Application<APIConfiguration> {

    protected WebsocketBundle websocket = new WebsocketBundle<>();

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
            // bootstrap.addBundle(new AWSCredentialsInitializerBundle<APIConfiguration>());
            ConfigInitializer.initialize();
        }

        bootstrap.addBundle(new TogglzBundle<APIConfiguration>());

        bootstrap.addBundle(new AuthenticationBundle<APIConfiguration>("Commons"));

        bootstrap.addBundle(new ViewBundle<APIConfiguration>());

        // for assets folder
        bootstrap.addBundle(new AssetsBundle());

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/commons/help", "/com/scottwseo/commons/help", "index.html", "help"));

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/commons/swagger", "/swagger", "index.ftl", "swagger"));

        bootstrap.addBundle(new AssetsBundle("/apidef","/apidef", "swagger.json", "swaggerdef"));

        bootstrap.addBundle(new ZipkinBundle<APIConfiguration>(getName()) {
            @Override
            public ZipkinFactory getZipkinFactory(APIConfiguration configuration) {
                return configuration.getZipkinFactory();
            }
        });

        bootstrap.addBundle(websocket);

    }

}
