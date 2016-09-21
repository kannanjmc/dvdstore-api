package com.scottwseo.commons.app;

import com.google.common.base.MoreObjects;
import com.scottwseo.commons.auth.AuthenticationBundle;
import com.scottwseo.commons.cfg.ArchaiusS3ConfigSourceBundle;
import com.scottwseo.commons.logging.ServerFactoryWrapper;
import com.scottwseo.commons.logging.WebsocketBundle;
import com.scottwseo.commons.resources.HelpResource;
import com.scottwseo.commons.togglz.TogglzBundle;
import com.scottwseo.commons.util.AWSCredentialsInitializerBundle;
import com.scottwseo.commons.util.EnvVariables;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.views.ViewBundle;
import org.apache.commons.io.IOUtils;

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
            bootstrap.addBundle(new ArchaiusS3ConfigSourceBundle<APIConfiguration>());
        }

        bootstrap.addBundle(new TogglzBundle<APIConfiguration>());

        bootstrap.addBundle(new AuthenticationBundle<APIConfiguration>("Commons"));

        bootstrap.addBundle(new ViewBundle<APIConfiguration>());

        // for assets folder
        bootstrap.addBundle(new AssetsBundle());

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/commons/help", "/com/scottwseo/commons/help", "index.html", "help"));

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/commons/swagger", "/swagger", "index.ftl", "swagger"));

        bootstrap.addBundle(new AssetsBundle("/apidef","/apidef", "swagger.json", "swaggerdef"));

        bootstrap.addBundle(websocket);

    }

    @Override
    public abstract String getName();

    /**
     * Override this method and call getAppVersion(appVersionFileInClassPath, defaultValue) in the overriding
     * method for convenience
     *
     * @return String app version
     */
    protected abstract String getAppVersion();

    protected String getAppVersion(String appVersionFileInClassPath, String defaultValue) {

        try {
            ClassLoader classLoader =
                    MoreObjects.firstNonNull(Thread.currentThread().getContextClassLoader(),
                            HelpResource.class.getClassLoader());

            return IOUtils.toString(classLoader.getResourceAsStream(appVersionFileInClassPath));
        }
        catch(Exception ioe) {
            return defaultValue;
        }

    }

    protected String applicationContextPath(APIConfiguration configuration) {
        ServerFactory server = configuration.getServerFactory();
        if (server instanceof DefaultServerFactory) {
            return ((DefaultServerFactory) server).getApplicationContextPath();
        }
        if (server instanceof ServerFactoryWrapper) {
            return ((DefaultServerFactory) ((ServerFactoryWrapper) server).getServerFactory()).getApplicationContextPath();
        }
        return null;
    }

}
